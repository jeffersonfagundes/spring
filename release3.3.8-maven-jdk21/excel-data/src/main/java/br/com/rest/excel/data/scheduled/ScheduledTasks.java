package br.com.rest.excel.data.scheduled;

import br.com.rest.excel.data.services.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ScheduledTasks {


    private final ExcelService excelService;

    @Scheduled(cron = "0 15 10 15 * ?")
    public void reportCurrentTime() throws IOException {
        excelService.extract("teste.xcl", 3L);
    }
}
