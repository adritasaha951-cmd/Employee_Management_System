package employee.management.system;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation; // Added for Bar Chart compatibility
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Analytics extends JFrame implements ActionListener {
    JButton back;

    Analytics() {
        super("Employee Analytics"); // Set window title
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        try {
            conn c = new conn();

            // Education Pie Chart Data
            ResultSet rs1 = c.statement.executeQuery("select education, count(*) from employee group by education");
            while (rs1.next()) {
                pieDataset.setValue(rs1.getString(1), rs1.getInt(2));
            }

            // Salary Bar Chart Data
            ResultSet rs2 = c.statement.executeQuery("select designation, avg(salary) from employee group by designation");
            while (rs2.next()) {
                barDataset.addValue(rs2.getDouble(2), "Salary", rs2.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Charts with standard parameters
        JFreeChart pieChart = ChartFactory.createPieChart("Education Distribution", pieDataset, true, true, false);

        // Bar chart often needs PlotOrientation in newer versions
        JFreeChart barChart = ChartFactory.createBarChart(
                "Avg Salary by Designation",
                "Designation",
                "Salary",
                barDataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel piePanel = new ChartPanel(pieChart);
        piePanel.setBounds(20, 20, 450, 400);
        add(piePanel);

        ChartPanel barPanel = new ChartPanel(barChart);
        barPanel.setBounds(500, 20, 450, 400);
        add(barPanel);

        back = new JButton("Back");
        back.setBounds(440, 450, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(1000, 550);
        setLocation(250, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Main_class();
    }

    public static void main(String[] args) {
        new Analytics();
    }
}