package source;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputStreamChanger {
    private final InputStream DEFAULT_INPUT_STREAM = System.in;
    private InputStream customInputStream;

    void changeInputStreamData(String data) {
        customInputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(customInputStream);
    }

    void restoreDefaultInputStream() {
        System.setIn(DEFAULT_INPUT_STREAM);
    }
}
