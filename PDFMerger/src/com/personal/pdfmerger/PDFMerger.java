package com.personal.pdfmerger;
 
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
 
public final class PDFMerger extends Application {
 
       private Desktop desktop = Desktop.getDesktop();
 
       @Override
       public void start(final Stage stage) {
              stage.setTitle("PDF Merger");
 
              final FileChooser fileChooser = new FileChooser();
              final Button openMultipleButton = new Button("Select PDF's to Merge...");
                    
              FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF Files(*.pdf)", "*.pdf");
              fileChooser.getExtensionFilters().add(filter);
             
              openMultipleButton.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(final ActionEvent e) {
                           List<File> list = fileChooser.showOpenMultipleDialog(stage);
                           if (list != null) {
                                  mergePDFFiles(list);
                           }
                     }
              });
 
              final GridPane inputGridPane = new GridPane();
              GridPane.setConstraints(openMultipleButton, 1, 0);
              inputGridPane.setHgap(6);
              inputGridPane.setVgap(6);
              inputGridPane.getChildren().addAll(openMultipleButton);
 
              final Pane rootGroup = new VBox(12);
              rootGroup.getChildren().addAll(inputGridPane);
              rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
              stage.setScene(new Scene(rootGroup));
              stage.show();
       }
 
       private void mergePDFFiles(List<File> files) {
              PDFMergerUtility mergePDF = new PDFMergerUtility();
              int count = 1;
              System.out.println("Merging process started...");
              for (File file : files) {
                     System.out.println("Merging File: " + file.getName());
                     mergePDF.addSource(file);
                     if (count == 1)
                           mergePDF.setDestinationFileName(file.getParent()
                                         + "\\MergedPDF.pdf");
              }
 
              try {
                     mergePDF.mergeDocuments();
                     System.out.println("Merge Completed. Destination File :"
                                  + mergePDF.getDestinationFileName());
              } catch (COSVisitorException | IOException e1) {
                     Logger.getLogger(PDFMerger.class.getName()).log(
                                  Level.SEVERE, null, e1);
              }
             File mergedDocument = new File(mergePDF.getDestinationFileName());
             try {
                     desktop.open(mergedDocument);
              } catch (IOException e) {
                     Logger.getLogger(PDFMerger.class.getName()).log(
                                  Level.SEVERE, null, e);
              }
       }
 
       public static void main(String[] args) {
              Application.launch(args);
       }
 
}