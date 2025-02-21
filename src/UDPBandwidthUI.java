import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Second;

public class UDPBandwidthUI {
    private JFrame frame;
    private JLabel uploadLabel, downloadLabel;
    private UDPServer server;
    private UDPClient client;
    private AtomicBoolean running;
    private TimeSeries uploadSeries, downloadSeries;

    public UDPBandwidthUI() {
        frame = new JFrame("UDP Bandwidth Monitor");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        uploadLabel = new JLabel("Upload Speed: 0 kbps", SwingConstants.CENTER);
        downloadLabel = new JLabel("Download Speed: 0 kbps", SwingConstants.CENTER);
        panel.add(uploadLabel);
        panel.add(downloadLabel);
        frame.add(panel, BorderLayout.NORTH);

        uploadSeries = new TimeSeries("Upload Speed");
        downloadSeries = new TimeSeries("Download Speed");

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(uploadSeries);
        dataset.addSeries(downloadSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Bandwidth Graph", "Time", "Speed (kbps)", dataset,
                true, true, false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        JButton stopButton = new JButton("Stop Program");
        stopButton.addActionListener(e -> stopProgram());
        frame.add(stopButton, BorderLayout.SOUTH);

        frame.setVisible(true);
        running = new AtomicBoolean(true);

        server = new UDPServer(this);
        client = new UDPClient(this);
        Thread thread1 = new Thread(server);
        Thread thread2 = new Thread(client);

        thread1.start();
        thread2.start();

    }

    public void stopProgram() {
        running.set(false);
        server.stop();
        client.stop();
        frame.dispose();
    }

    public void updateUploadSpeed(double speed) {
        SwingUtilities.invokeLater(() -> {
            uploadLabel.setText(String.format("Upload Speed: %.2f kbps", speed));
            uploadSeries.addOrUpdate(new Second(), speed);
        });
    }

    public void updateDownloadSpeed(double speed) {
        SwingUtilities.invokeLater(() -> {
            downloadLabel.setText(String.format("Download Speed: %.2f kbps", speed));
            downloadSeries.addOrUpdate(new Second(), speed);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UDPBandwidthUI::new);
    }
}
