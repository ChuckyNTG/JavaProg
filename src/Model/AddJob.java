package View;

import java.time.LocalDate;
import java.util.Calendar;

import Model.Job;
import Model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddJob
{
	Model _model;
	JobPane anchorPane;
	Stage addJobStage;
	RadioButton chk;
	String stringDate;
	LocalDate date;
	Job _job;
	boolean windowClosed;
	
	public AddJob(Model model)
	{
		_model = model;
		
	}
	
	public void display()
	{
		Label name = new Label ("Name: ");
		name.setLayoutX(80);
		name.setLayoutY(50);
		Label priority = new Label("Priority: ");
		priority.setLayoutX(80);
		priority.setLayoutY(87);
		Label dueDate = new Label("Due Date: ");
		dueDate.setLayoutX(80);
		dueDate.setLayoutY(124);
		
		Label description = new Label("Description: ");
		description.setLayoutX(80);
		description.setLayoutY(161);
		
		TextField jobName = new TextField();
		jobName.setLayoutX(170);
		jobName.setLayoutY(45);
		jobName.setPrefSize(233, 31);
		
		TextArea descriptionInfo = new TextArea();
		
		descriptionInfo.setPrefSize(219, 69);
		descriptionInfo.setLayoutX(170);
		descriptionInfo.setLayoutY(158);
		
		DatePicker dueDatePicker = new DatePicker();
		dueDatePicker.setPrefSize(233, 31);
		dueDatePicker.setLayoutX(170);
		dueDatePicker.setLayoutY(117);
		dueDatePicker.setValue(LocalDate.now());
		date = LocalDate.now();
		stringDate= date.toString();
		dueDatePicker.setOnAction(event->{
			date = dueDatePicker.getValue();
			stringDate= date.toString();
			System.out.println(stringDate);
		});
		
		ToggleGroup group = new ToggleGroup();
		
		RadioButton low = new RadioButton("Low");
		low.setSelected(true);
		low.setToggleGroup(group);
		low.setLayoutX(170);
		low.setLayoutY(87);
		RadioButton medium = new RadioButton("Medium");
		medium.setToggleGroup(group);
		medium.setLayoutX(240);
		medium.setLayoutY(87);
		RadioButton high = new RadioButton("High");
		high.setToggleGroup(group);
		high.setLayoutX(334);
		high.setLayoutY(87);
		
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
				{

					@Override
					public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2)
					{
						chk = (RadioButton)arg2.getToggleGroup().getSelectedToggle();
						
					}
			
				});
		
		Button addTask = new Button("Add");
		addTask.setLayoutX(392);
		addTask.setLayoutY(250);
		addTask.setOnAction(e -> {
			String jobNameText = jobName.getText();
			String jobDescriptionText = descriptionInfo.getText();
			//In case radio button doesnt change
			if(chk == null)
			{
				chk=low;
			}
			
			_job = _model.backup.add(jobName.getText(),descriptionInfo.getText(),chk.getText(),stringDate);
			System.out.println("Total is " + _model.backup.getList().size());
			//_model.add(_job);
			this.addJob();
			windowClosed = true;
			addJobStage.close();
			});
		
		AnchorPane jobContents = new AnchorPane();
		jobContents.setStyle("-fx-background-color: #1d1d1d");
		jobContents.setPrefSize(400, 200);
		jobContents.getChildren().addAll(name,priority,dueDate,description,dueDatePicker,
											low,medium,high,jobName,descriptionInfo,addTask);
		TitledPane addJob = new TitledPane();
		addJob.setText("Add");
		addJob.setContent(jobContents);
		addJob.setCollapsible(false);
		addJob.setAnimated(false);
		
		addJobStage = new Stage();
		String css = this.getClass().getResource("cssstyling.css").toExternalForm();
		addJob.getStylesheets().add(css);

		Scene scene = new Scene(addJob);
		addJobStage.setScene(scene);
		addJobStage.showAndWait();
	}
	
	public void addJob()
	{
		anchorPane = new JobPane();
		anchorPane.setName(_job.getName());  
        anchorPane.setDescription(_job.getDescription());
       
        int priority =_job.getPriority();
        String priorityString = "Low";
		if(priority == 3)
			priorityString = "High";
		if(priority == 2)
			priorityString = "Medium" ;
		 anchorPane.setPriority(priorityString);
      //theres probably a better way todo this but I was tired
        int year = _job.getDate().get(Calendar.YEAR);
        int month = _job.getDate().get(Calendar.MONTH);
        int dayOfMonth = _job.getDate().get(Calendar.DAY_OF_MONTH);
        String date = String.valueOf(month+1);
        date += "/" + String.valueOf(dayOfMonth);
        date += "/" + String.valueOf(year);
        anchorPane.setDate(date);
	}
	
	public void setJob(int index)
	{
		_job = _model.getList().get(index);
	}
	
	public JobPane getJobPane()
	{
		return anchorPane;
	}
	
	public boolean getWindowClosed()
	{
		return windowClosed;
	}
	
	public Job getJob()
	{
		return _job;
	}
}
