package com.dantsu.escposprinter;

import com.dantsu.escposprinter.connection.bytearray.ByteArrayConnection;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void t02() throws Exception {
        ByteArrayConnection device = new ByteArrayConnection();
        EscPosPrinter printer = new EscPosPrinter(
                device, 203, 48f, 32,
                new EscPosCharsetEncoding("GBK", 0)
        );
        printer.printFormattedTextAndCut(
                "[L]<b>BEAUTIFUL SHIRT 中文</b>[R]9.99e\n" +
                "[L]  + Size : S\n"
        );
        printer.disconnectPrinter();
        System.out.println( device.toBase64() );

        printer = new EscPosPrinter(
                new TcpConnection("192.168.0.250", 9100, 3 * 1000), 203, 48f, 32,
                new EscPosCharsetEncoding("GBK", 0)
        );
        byte[] bytes = Base64.getDecoder().decode( device.toBase64() );
        printer.printFormattedText( new String(bytes, "GBK") );
    }


    @Test
    public void t01() {
        try {
            EscPosPrinter printer = new EscPosPrinter(
                    new TcpConnection("192.168.0.250", 9100, 3 * 1000), 203, 48f, 32,
                    new EscPosCharsetEncoding("GBK", 0)
            );
            printer.printFormattedText(
                    "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                            "[L]\n" +
                            "[C]================================\n" +
                            "[L]\n" +
                            "[L]<b>BEAUTIFUL SHIRT 中文</b>[R]9.99e\n" +
                            "[L]  + Size : S\n" +
                            "[L]\n" +
                            "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                            "[L]  + Size : 57/58\n" +
                            "[L]\n" +
                            "[C]--------------------------------\n" +
                            "[R]TOTAL PRICE :[R]34.98e\n" +
                            "[R]TAX :[R]4.23e\n" +
                            "[L]\n" +
                            "[C]================================\n" +
                            "[L]\n" +
                            "[L]<font size='tall'>Customer :</font>\n" +
                            "[L]Raymond DUPONT\n" +
                            "[L]5 rue des girafes\n" +
                            "[L]31547 PERPETES\n" +
                            "[L]Tel : +33801201456\n" +
                            "[L]\n"
            );
            printer.disconnectPrinter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}