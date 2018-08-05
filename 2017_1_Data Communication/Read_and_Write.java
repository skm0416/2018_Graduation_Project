//2012004021 심규민
import java.io.*;
import java.util.*;
import gnu.io.*;

public class Read_and_Write implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;
    SerialPort serialPort;
    OutputStream outputStream;
    InputStream inputStream;
    Thread readThread;
    
    public static void main(String[] args) {
        // 시스템에 있는 가능한 드라이버의 목록을 받아온다.
        portList = CommPortIdentifier.getPortIdentifiers();

        // enumeration type 인 portList 의 모든 객체에 대하여
        while (portList.hasMoreElements()) {
            // enumeration 에서 객체를 하나 가져온다.
            portId = (CommPortIdentifier) portList.nextElement();
            // 가져온 객체의 port type 이 serial port 이면
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                
                if (portId.getName().equals("COM3")) {

                	Read_and_Write reader = new Read_and_Write();
                    }
                }
            }
        }
 // SimpleRead 생성자
    public Read_and_Write() {
        Scanner scan=new Scanner(System.in);
        try {
            /* 사용 메소드 : 
               public CommPort open(java.lang.String appname, int timeout)
               기능 : 
               어플리케이션 이름과 타임아웃 시간 명시 */
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) { }
        try {
            // 시리얼 포트에서 입력 스트림을 획득한다.
            inputStream = serialPort.getInputStream();
        } catch (IOException e) { }
        try {
            /* 사용 메소드 : 
               public CommPort open(java.lang.String appname, int timeout)
               기능 : 
               어플리케이션 이름과 타임아웃 시간 명시 */
            serialPort = (SerialPort) portId.open("SimpleWriteApp",2000);
        } catch (PortInUseException e) { }
        try {
            // 시리얼 포트에서 입력 스트림을 획득한다.
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) { }

        // 시리얼 포트의 이벤트 리스너로 자신을 등록한다.
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) { }
        
        /* 시리얼 포트에 데이터가 도착하면 이벤트가 한 번 발생되는데
           이 때, 자신이 리스너로 등록된 객체에게 이벤트를 전달하도록 허용. */
        serialPort.notifyOnDataAvailable(true);

        // 시리얼 통신 설정. Data Bit는 8, Stop Bit는 1, Parity Bit는 없음.
        try {
            serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8, SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
             } catch (UnsupportedCommOperationException e) { }
        // 쓰레드 객체 생성
        readThread = new Thread(this);

        // 쓰레드 동작
        readThread.start();
        String temp;
        while(true){
        	temp=scan.nextLine();
        try {
            outputStream.write(temp.getBytes());
        } catch (IOException e){}
        }
    }
    public void run() {
    try {
        Thread.sleep(20000);
    } catch (InterruptedException e) { }
}
 // 시리얼 포트 이벤트가 발생하면 호출. 시리얼 포트 이벤트를 전달한다.
    public void serialEvent(SerialPortEvent event) {
        // 이벤트의 타입에 따라 switch 문으로 제어.
        switch (event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:break;
            // 데이터가 도착하면
        case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[1];    // byte 배열 객체 생성
                // 입력 스트림이 사용가능하면, 버퍼로 읽어 들인 후
                // String 객체로 변환하여 출력
                int numBytes=-1;
                try {
                    while ((numBytes=inputStream.read(readBuffer))>-1) {//이부분 SimpleRead.java파일대로 하니 정상적으로 읽어오질 못해서 인터넷 검색후 수정했습니다
                    System.out.print(new String(readBuffer,0,numBytes));
                    }
                } catch (IOException e) { }
                break;
            }
        }
    }