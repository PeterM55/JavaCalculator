/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator55java;

/**
 *
 * @author Pmit
 */
public class Calculator extends javax.swing.JFrame {

    /**
     * Creates new form Calculator
     */
    public Calculator() {
        initComponents();
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }
    
    private boolean isDouble(String numberToTest) {
        try {
          Double.parseDouble(numberToTest);
          return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    
    private void submit() {
        text = inputBox.getText();
        text = text.replaceAll(" ", "");
        
        String[] splitString = new String[text.length()];
        int endLength = text.length();
        
        
        splitString = text.split("");
        
        for (int i = 0; i < splitString.length-1; i++) {
            if ((isDouble(splitString[i]) || splitString[i].equals(".")) && (isDouble(splitString[i+1]) || splitString[i + 1].equals("."))) {
                if ((splitString[i].equals(".") &&  splitString[i + 1].equals(".")) || (splitString[i + 1] == "." && isDouble(splitString[i]) && Double.parseDouble(splitString[i]) % 1 != 0))
                {
                    outputBox.setText("Two decimal places in one number detected.");
                    return;
                }
                splitString[i + 1] = splitString[i] + splitString[i + 1];
                splitString[i] = "";
                endLength--;
            } else if (splitString[i].equals("*") && splitString[i + 1].equals("**")) {
                splitString[i + 1] = "^";
                splitString[i] = "";
                endLength--;
            }
        }

        String[] equation = new String[endLength];
        int atIndex = 0;

        for (int i = 0; i < splitString.length; i++) {
            if (splitString[i] != "")
            {
                equation[atIndex++] = splitString[i];
            }
        }
        
        //System.out.println("test1: "+String.join("",splitString));
        
        int onbed = 0;
        //Double num1 = 0;
        //Double num2 = 0;
        while (true) {
            for (int i = 0; i < equation.length; i++) {
                if (i+2 < equation.length && equation[i].equals("(") && equation[i+2].equals( ")")) {
                    equation[i] = equation[i+1];
                    equation[i + 1] = "";
                    equation[i + 2] = "";
                    equation = removeEmpty(equation);
                    onbed = 0;
                }

                if (onbed == 1)
                {
                    //System.out.println("test1.5: "+i+" "+equation.length+" "+String.join("",equation));
                    if (i > 0 && i < equation.length - 1 && equation[i].equals("^") && (isDouble(equation[i-1]) && (isDouble(equation[i+1]))))
                    {
                        equation[i - 1] = ""+Math.pow(Double.parseDouble(equation[i-1]), Double.parseDouble(equation[i+1]));
                        equation[i] = "";
                        equation[i + 1] = "";
                        equation = removeEmpty(equation);
                        onbed = 0;
                        continue;
                    }
                }
                else if (onbed == 2)
                {
                   // helloWorldLabel.Text = "1";

                    if (i > 0 && i < equation.length - 1 && (equation[i].equals("*") || equation[i].equals("/")) && (isDouble(equation[i-1]) && isDouble(equation[i+1]))
                        && !(i > 1 && equation[i - 2].equals("^")) && !(i < equation.length - 2 && equation[i + 2].equals("^"))
                        )
                    {
                       // helloWorldLabel.Text = "2";
                        if (equation[i].equals("*"))
                        {
                            equation[i - 1] = ""+(Double.parseDouble(equation[i-1]) * Double.parseDouble(equation[i+1]));
                            equation[i] = "";
                            equation[i + 1] = "";
                            //System.out.println("test1.5: "+i+" "+equation.length+" "+String.join("_",equation));
                            equation = removeEmpty(equation);
                            //System.out.println("test1.6: "+i+" "+equation.length+" "+String.join("_",equation));
                            onbed = 0;
                            continue;
                        }
                        else if (equation[i].equals("/"))
                        {
                            equation[i - 1] = ""+(Double.parseDouble(equation[i-1]) / Double.parseDouble(equation[i+1]));
                            equation[i] = "";
                            equation[i + 1] = "";
                            equation = removeEmpty(equation);
                            onbed = 0;
                            continue;
                        }
                    }
                    if (i < equation.length - 1 && isDouble(equation[i]) && isDouble(equation[i+1])) {
                        equation[i] = ""+(Double.parseDouble(equation[i]) * Double.parseDouble(equation[i+1]));
                        equation[i + 1] = "";
                        equation = removeEmpty(equation);
                        onbed = 0;
                        continue;
                    }
                }
                else if (onbed == 3)
                {
                    //helloWorldLabel.Text = i.ToString() + (i < equation.Length - 3).ToString() + "  end result: " +
                    //    (!(i < equation.Length - 3 && equation[i + 2] != "+" && equation[i + 2] != "-")).ToString();

                    //helloWorldLabel.Text = ((i < 2 || (i > 1 && (equation[i - 2].equals("+") || equation[i - 2].equals("-")))).ToString() + (i >= equation.Length - 3 || (i < equation.Length - 3 && (equation[i + 2].equals("+") || equation[i + 2].equals("-"))))
                    //     ).ToString();
                    //equation = removeEmpty(equation);
                    //helloWorldLabel.Text = (i >= equation.Length - 3).ToString()+"  i:"+ i+"  el3:"+(equation.Length)+"     "+ equation[equation.Length-1];
                    //if (i == 1) break;
                    if (i > 0 && i < equation.length - 1 && (equation[i].equals("+") || equation[i].equals("-")) && (isDouble(equation[i-1]) && (isDouble(equation[i+1])))
                        && (i < 2 || (i > 1 && (equation[i - 2].equals("+") || equation[i - 2].equals("-") || equation[i - 2].equals("(")))) && (i >= equation.length - 3 || (i < equation.length - 3 && (equation[i + 2].equals("+") || equation[i + 2].equals("-") || equation[i + 2].equals( ")"))))
                        )
                    {

                        if (equation[i].equals("+"))
                        {

                            equation[i - 1] = ""+(Double.parseDouble(equation[i-1]) + Double.parseDouble(equation[i+1]));
                            equation[i] = "";
                            equation[i + 1] = "";
                            equation = removeEmpty(equation);
                            onbed = 0;
                            continue;
                        }
                        else if (equation[i].equals("-"))
                        {
                            equation[i - 1] = ""+(Double.parseDouble(equation[i-1]) - Double.parseDouble(equation[i+1]));
                            equation[i] = "";
                            equation[i + 1] = "";
                            equation = removeEmpty(equation);
                            onbed = 0;
                            continue;
                        }
                    }
                }

            }
            onbed++;
            if (onbed == 4) break;
        }

        outputBox.setText(String.join("",equation));
        //System.out.println("test2: "+String.join("",equation));
    }
    
    String text = "";
    private void updateText(String stringToAdd) {
        text = inputBox.getText();
        text = text + stringToAdd;
        inputBox.setText(text);
    }
    
    private String[] removeEmpty(String[] stringToFix) {
        //
        int newLength = stringToFix.length;
        for (int i = 0; i < stringToFix.length; i++) {
            if (stringToFix[i].equals(""))
            newLength--;
        }
        
        String[] newArray = new String[newLength];
        int count = 0;
        for (int i = 0; i < stringToFix.length; i++) {
            if (!stringToFix[i].equals("")) {
                newArray[count] = stringToFix[i];
                count++;
            }
        }
        
        return newArray;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        num0 = new javax.swing.JButton();
        num1 = new javax.swing.JButton();
        num3 = new javax.swing.JButton();
        num2 = new javax.swing.JButton();
        num6 = new javax.swing.JButton();
        num5 = new javax.swing.JButton();
        num4 = new javax.swing.JButton();
        num9 = new javax.swing.JButton();
        num8 = new javax.swing.JButton();
        num7 = new javax.swing.JButton();
        decimalB = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputBox = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputBox = new javax.swing.JTextPane();
        leftBracketButton = new javax.swing.JButton();
        rightBracketButton = new javax.swing.JButton();
        divideButton = new javax.swing.JButton();
        multiplyButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        equalsButton = new javax.swing.JButton();
        SubtractButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        num0.setText("0");
        num0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num0ActionPerformed(evt);
            }
        });

        num1.setText("1");
        num1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num1ActionPerformed(evt);
            }
        });

        num3.setText("3");
        num3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num3ActionPerformed(evt);
            }
        });

        num2.setText("2");
        num2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num2ActionPerformed(evt);
            }
        });

        num6.setText("6");
        num6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num6ActionPerformed(evt);
            }
        });

        num5.setText("5");
        num5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num5ActionPerformed(evt);
            }
        });

        num4.setText("4");
        num4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num4ActionPerformed(evt);
            }
        });

        num9.setText("9");
        num9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num9ActionPerformed(evt);
            }
        });

        num8.setText("8");
        num8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num8ActionPerformed(evt);
            }
        });

        num7.setText("7");
        num7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num7ActionPerformed(evt);
            }
        });

        decimalB.setText(".");
        decimalB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decimalBActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(inputBox);

        jScrollPane2.setViewportView(outputBox);

        leftBracketButton.setText("(");
        leftBracketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftBracketButtonActionPerformed(evt);
            }
        });

        rightBracketButton.setText(")");
        rightBracketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightBracketButtonActionPerformed(evt);
            }
        });

        divideButton.setText("/");
        divideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divideButtonActionPerformed(evt);
            }
        });

        multiplyButton.setText("*");
        multiplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiplyButtonActionPerformed(evt);
            }
        });

        addButton.setText("+");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        equalsButton.setText("=");
        equalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equalsButtonActionPerformed(evt);
            }
        });

        SubtractButton.setText("-");
        SubtractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubtractButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(num7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(num8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(num9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(leftBracketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rightBracketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(num4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(num5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(num6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(num1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(num2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(15, 15, 15)
                                    .addComponent(num3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(num0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(decimalB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(multiplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(divideButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(SubtractButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(equalsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(num7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(num9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(num8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leftBracketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rightBracketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(num4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(num6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(num5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(multiplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divideButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num0, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(decimalB, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SubtractButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(equalsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void num0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num0ActionPerformed
        updateText("0");
    }//GEN-LAST:event_num0ActionPerformed

    private void num1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num1ActionPerformed
        updateText("1");
    }//GEN-LAST:event_num1ActionPerformed

    private void num3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num3ActionPerformed
        updateText("3");
    }//GEN-LAST:event_num3ActionPerformed

    private void num2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num2ActionPerformed
        updateText("2");
    }//GEN-LAST:event_num2ActionPerformed

    private void num6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num6ActionPerformed
        updateText("6");
    }//GEN-LAST:event_num6ActionPerformed

    private void num5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num5ActionPerformed
        updateText("5");
    }//GEN-LAST:event_num5ActionPerformed

    private void num4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num4ActionPerformed
        updateText("4");
    }//GEN-LAST:event_num4ActionPerformed

    private void num9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num9ActionPerformed
        updateText("9");
    }//GEN-LAST:event_num9ActionPerformed

    private void num8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num8ActionPerformed
        updateText("8");
    }//GEN-LAST:event_num8ActionPerformed

    private void num7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num7ActionPerformed
        updateText("7");
    }//GEN-LAST:event_num7ActionPerformed

    private void decimalBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decimalBActionPerformed
        updateText(".");
    }//GEN-LAST:event_decimalBActionPerformed

    private void leftBracketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftBracketButtonActionPerformed
        updateText("(");
    }//GEN-LAST:event_leftBracketButtonActionPerformed

    private void rightBracketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightBracketButtonActionPerformed
        updateText(")");
    }//GEN-LAST:event_rightBracketButtonActionPerformed

    private void divideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divideButtonActionPerformed
        updateText("/");
    }//GEN-LAST:event_divideButtonActionPerformed

    private void multiplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiplyButtonActionPerformed
        updateText("*");
    }//GEN-LAST:event_multiplyButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        updateText("+");
    }//GEN-LAST:event_addButtonActionPerformed

    private void equalsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equalsButtonActionPerformed
        submit();
    }//GEN-LAST:event_equalsButtonActionPerformed

    private void SubtractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubtractButtonActionPerformed
        updateText("-");
    }//GEN-LAST:event_SubtractButtonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubtractButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton decimalB;
    private javax.swing.JButton divideButton;
    private javax.swing.JButton equalsButton;
    private javax.swing.JTextPane inputBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton leftBracketButton;
    private javax.swing.JButton multiplyButton;
    private javax.swing.JButton num0;
    private javax.swing.JButton num1;
    private javax.swing.JButton num2;
    private javax.swing.JButton num3;
    private javax.swing.JButton num4;
    private javax.swing.JButton num5;
    private javax.swing.JButton num6;
    private javax.swing.JButton num7;
    private javax.swing.JButton num8;
    private javax.swing.JButton num9;
    private javax.swing.JTextPane outputBox;
    private javax.swing.JButton rightBracketButton;
    // End of variables declaration//GEN-END:variables
}
