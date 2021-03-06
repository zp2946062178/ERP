package MyFileUI.OverFlowAndLack;

import Login.CurrentState;
import MockObject.Warehousebl;
import MyFileUI.MyFile;
import Others.SolveUpdate;
import Start.RemoteHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class OverflowController {
    @FXML public Line line1;
    @FXML public Circle circle1;
    @FXML public Line line2;
    @FXML public Circle circle2;
    @FXML public Label SubmitL;
    @FXML public VBox VBoxMessage;
    @FXML public Label title;
    @FXML public VBox V;
    @FXML public TextField name;
    @FXML public TextField person;
    @FXML public ComboBox warehosue;
    @FXML public Label MoneySum;
    int Click2Temp =0;

    public void Clicked1(MouseEvent mouseEvent) {
        Scenes s=new Scenes();
        //1.初始化AddPane
        URL url=getClass().getResource("AddPaneOverflow.fxml");
        if(s.getState().equals("报溢单")){
            url=getClass().getResource("AddPaneOverflow.fxml");
        }
        else{
            url=getClass().getResource("AddPaneLack.fxml");
        }

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> fxmlNamespace = loader.getNamespace();
        FlowPane flowpane1= (FlowPane) fxmlNamespace.get("flowpane1");
        Label title= (Label) fxmlNamespace.get("title");
        Label label1= (Label) fxmlNamespace.get("lable1");

        Warehousebl warehouse=new Warehousebl();
        ArrayList<String> array=warehouse.getOnlyWarehouse();
        for(int i=0;i<array.size();i++){
            URL suburl=getClass().getResource("WarehouseImage.fxml");
            FXMLLoader subloader = new FXMLLoader(suburl);
            Parent subroot = null;
            try {
                subroot = subloader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, Object> subfxmlNamespace = subloader.getNamespace();
            Label namel= (Label) subfxmlNamespace.get("namelW");
            namel.setText(array.get(i));
            flowpane1.getChildren().add(subroot);
        }

        //2.将现有的Pane加入到Scenes中去

        s.addParent(root);
        s.addLoader(loader);
        s.addLocation(title.getText());
        label1.setText("当前位置 "+s.getLocation());

        //3.设置界面的显示结果
        startChoose controller=new startChoose();
        controller.getHBox().getChildren().clear();
        controller.getHBox().getChildren().add(root);

    }

    public void Entered1(MouseEvent mouseEvent) {
        line1.setStroke(Paint.valueOf("#f2f2f2"));
        circle1.setFill(Paint.valueOf("#f2f2f2"));
    }

    public void Exited1(MouseEvent mouseEvent) {
        line1.setStroke(Paint.valueOf("#ffffff"));
        circle1.setFill(Paint.valueOf("#ffffff"));
    }

    public void Clicked2(MouseEvent mouseEvent) {
        String frame="";
        if(Click2Temp ==0){
            frame="AddPart003.fxml";
            Click2Temp =1;
        }
        else{
            frame="AddPart004.fxml";
            Click2Temp =0;
        }
        try {
            Parent root= FXMLLoader.load(getClass().getResource(frame));
            VBoxMessage.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Entered2(MouseEvent mouseEvent) {
        line2.setStroke(Paint.valueOf("#f2f2f2"));
        circle2.setFill(Paint.valueOf("#f2f2f2"));
    }

    public void Exited2(MouseEvent mouseEvent) {
        line2.setStroke(Paint.valueOf("#ffffff"));
        circle2.setFill(Paint.valueOf("#ffffff"));
    }

    public void Clicked3(MouseEvent mouseEvent) {
        SolveUpdate su=new SolveUpdate();
        su.updateOverFlowAndLack(name.getText(),person.getText(),"",V,Double.parseDouble(MoneySum.getText().substring(8)),VBoxMessage);
    }

    public void Entered3(MouseEvent mouseEvent) {
        SubmitL.setFont(Font.font(20));
    }

    public void Exited3(MouseEvent mouseEvent) {
        SubmitL.setFont(Font.font(18));
    }

    public void Clicked4(MouseEvent mouseEvent) {
        try {
            RemoteHelper.getInstance().getServiceFactory().getWarehouseManBusinessLogicService().commit(CurrentState.getLoginID(),name.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        MyFile.draft.refresh();
        MyFile.wait.refresh();
        MyFile.done.refresh();
        MyFile.trash.refresh();
    }

    public void Entered4(MouseEvent mouseEvent) {
    }

    public void Exited4(MouseEvent mouseEvent) {
    }
}
