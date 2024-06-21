package com.gshmalyukh.demo;

import com.gshmalyukh.demo.scheduling.TreatmentPlanGenerator;
import com.gshmalyukh.demo.service.TreatmentPlanService;
import com.gshmalyukh.demo.util.PrimitiveFileWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.gshmalyukh.demo.core.TreatmentPlan;

import java.util.Date;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

//		utilityTasks(ctx);
	}

	private static void utilityTasks(ConfigurableApplicationContext ctx){
		String newLine = System.getProperty("line.separator");
		String separator = "," + newLine;

		TreatmentPlanGenerator treatmentPlanGenerator = new TreatmentPlanGenerator(10);
		treatmentPlanGenerator.generateTestSqlFile();

		var service = ctx.getBean(TreatmentPlanService.class);
		String data = service.findAll().parallel().map(TreatmentPlan::toString).collect(Collectors.joining(separator));
		PrimitiveFileWriter.writeToFile(data, "findAll.txt");

		service = ctx.getBean(TreatmentPlanService.class);
		data = service.findByEndTimeAfterOrNullAndStartTimeBeforeEquals(new Date()).parallel().map(TreatmentPlan::toString).collect(Collectors.joining(separator));
		PrimitiveFileWriter.writeToFile(data, "findByTime.txt");


	}

}
