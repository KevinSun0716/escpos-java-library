package com.dantsu.escposprinter.connection.bytearray;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class ByteArrayConnection extends DeviceConnection {

    private ByteArrayOutputStream baos;

    public ByteArrayConnection() {
        super();
        baos = new ByteArrayOutputStream();
    }

    public byte[] toArray() {
        return baos.toByteArray();
    }

    public String toBase64() throws Exception {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(baos.toByteArray());
    }

    @Override
    public DeviceConnection connect() throws EscPosConnectionException {
        this.outputStream = baos;
        this.data = new byte[0];
        return this;
    }

    @Override
    public DeviceConnection disconnect() {
        this.data = new byte[0];
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
                this.outputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

}