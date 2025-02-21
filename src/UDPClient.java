import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient implements Runnable {
    private volatile boolean running = true;
    private UDPBandwidthUI ui;

    public UDPClient(UDPBandwidthUI ui) {
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            InetAddress serverIP = InetAddress.getByName("localhost");
            int serverPort = 9876;
            DatagramSocket socket = new DatagramSocket();

            byte[] data = new byte[1024];
            long totalBytesSent = 0;
            long startTime = System.currentTimeMillis();

            while (running) {
                DatagramPacket packet = new DatagramPacket(data, data.length, serverIP, serverPort);
                socket.send(packet);
                totalBytesSent += data.length;

                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= 1000) {
                    // Convert bandwidth from bytes to kilobits
                    double bandwidth = (totalBytesSent * 8.0) / (currentTime - startTime); // in kilobits
                    ui.updateUploadSpeed(bandwidth / 1000); // Convert to kbps
                    totalBytesSent = 0;
                    startTime = currentTime;
                }
                Thread.sleep(10);
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
