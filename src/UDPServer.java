import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer implements Runnable {
    private volatile boolean running = true;
    private UDPBandwidthUI ui;

    public UDPServer(UDPBandwidthUI ui) {
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            byte[] buffer = new byte[1024];
            long totalBytesReceived = 0;
            long startTime = System.currentTimeMillis();

            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                totalBytesReceived += packet.getLength();

                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= 1000) {
                    // Convert bandwidth from bytes to kilobits
                    double bandwidth = (totalBytesReceived * 8.0) / (currentTime - startTime); // in kilobits
                    ui.updateDownloadSpeed(bandwidth / 1000); // Convert to kbps
                    totalBytesReceived = 0;
                    startTime = currentTime;
                }
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }
}
