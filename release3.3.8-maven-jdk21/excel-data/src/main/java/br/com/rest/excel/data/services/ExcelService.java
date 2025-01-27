package br.com.rest.excel.data.services;

import br.com.rest.excel.data.client.PersonClient;
import br.com.rest.excel.data.dto.PersonDTO;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

@Service
@AllArgsConstructor
public class ExcelService {

    private final PersonClient personClient;

    public void extract(String nameFile, Long timeLimited) throws IOException {
        var tempo = System.currentTimeMillis();
        FileInputStream file = new FileInputStream(ResourceUtils.getFile("classpath:" + nameFile));

        Workbook workbook = WorkbookFactory.create(file);

        try{
            Sheet sheetPersons = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheetPersons.iterator();

            while (rowIterator.hasNext() || (System.currentTimeMillis()-tempo/1000) < timeLimited) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();

                var personDto = extractPersonDto(cell, cellIterator);
                processPerson(row, cell, cellIterator, personDto);

            }
            file.close();

            FileOutputStream outFile = new FileOutputStream(ResourceUtils.getFile("classpath:" + nameFile));
            workbook.write(outFile);
            outFile.close();
        } catch (Exception e) {
            file.close();

            FileOutputStream outFile = new FileOutputStream(ResourceUtils.getFile("classpath:" + nameFile));
            workbook.write(outFile);
            outFile.close();
            throw new RuntimeException(e);
        }
    }

    private void processPerson(Row row, Cell cell, Iterator<Cell> cellIterator, PersonDTO personDto) {
        row.createCell(cell.getColumnIndex() + 1);
        cell = cellIterator.next();
        try {
            var personResponse = personClient.criarPessoa(personDto);
            cell.setCellValue(personResponse.toString());
        } catch (Exception e) {
            cell.setCellValue(e.getMessage());
        }
    }

    private PersonDTO extractPersonDto(Cell cell, Iterator<Cell> cellIterator) {
        PersonDTO personDTO = new PersonDTO();
        while (cellIterator.hasNext()) {
            cell = cellIterator.next();
            switch (cell.getColumnIndex()) {
                case 0:
                    personDTO.setName(cell.getStringCellValue());
                    break;
                case 1:
                    personDTO.setDocument(cell.getStringCellValue());
                    break;
                case 2:
                    personDTO.setGender(cell.getStringCellValue());
                    break;
                case 3:
                    personDTO.setDateOfBirth(LocalDate.parse(cell.getStringCellValue()));
                    break;
                case 4:
                    personDTO.setFullAddress(cell.getStringCellValue());
                    break;
            }
        }
        return personDTO;

    }
}
