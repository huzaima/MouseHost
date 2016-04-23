package MouseHost;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Huzaima Khan
 */
public class MouseHost {

    public static void main(String[] args) throws IOException, AWTException {

        Point p;
        int x, y, read;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Mouse Sensitivity: ");
        int sensitivity = scanner.nextInt();
        Robot robot = new Robot();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Server created on " + serverSocket.toString());
        System.out.println("Waiting for client...");

        Socket clientSocket = serverSocket.accept();

        System.out.println("New client joined " + clientSocket.toString() + "\t" + clientSocket.getInetAddress().toString());

        BufferedReader clientStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (clientSocket.isConnected() && !clientSocket.isClosed()) {
            read = clientStream.read();
            p = MouseInfo.getPointerInfo().getLocation();
            x = p.x;
            y = p.y;
            switch (read) {
                case 1:
                    if(y>0)
                        robot.mouseMove(x, y=y-sensitivity);
                    break;
                case 2:
                    if(y<screenSize.height)
                        robot.mouseMove(x, y=y+sensitivity);
                    break;
                case 3:
                    if(x>0)
                        robot.mouseMove(x=x-sensitivity, y);
                    break;
                case 4:
                    if(x<screenSize.width)
                        robot.mouseMove(x=x+sensitivity, y);
                    break;
                case 5:
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case 6:
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    break;
            }
        }
    }

}