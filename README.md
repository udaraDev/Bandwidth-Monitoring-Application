UDP Bandwidth Monitor

Overview

The UDP Bandwidth Monitor is a desktop application that measures and displays real-time upload and download speeds by sending and receiving UDP packets between a client and a server. The application uses multithreading to handle client and server operations concurrently and visualizes the bandwidth data using a dynamic graph.

Features

Real-Time Bandwidth Monitoring: Measures upload and download speeds in real-time.
Multithreading: Uses separate threads for the client (upload) and server (download) to ensure concurrent operations.
Dynamic Graph: Visualizes bandwidth data using JFreeChart.
User-Friendly Interface: Built using Java Swing for a simple and intuitive UI.
Graceful Shutdown: Stops threads and releases resources properly when the program is closed.

How It Works

Key Components

1. UDPBandwidthUI:
The main class that sets up the user interface and starts the client and server threads.

2. UDPClient:
A thread that sends UDP packets to the server to measure upload speed.

3. UDPServer:
A thread that receives UDP packets from the client to measure download speed.

Threading

The client and server run in separate threads to ensure concurrent upload and download operations.
The running flag controls the execution of the threads, allowing them to be stopped gracefully.

Bandwidth Calculation

Upload and download speeds are calculated every second using the formula:
Bandwidth (Mbps) = (Total Bytes Sent/Received * 8) / Time Elapsed (in milliseconds)

Graph Visualization

The application uses JFreeChart to plot the upload and download speeds over time.

Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch: git checkout -b feature/your-feature-name
3. Commit your changes: git commit -m "Add your feature"
4. Push to the branch:git push origin feature/your-feature-name
5. Open a pull request.

