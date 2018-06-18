package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by thomas_young on 18/6/2018.
 */
public interface Resource {

    String getDescription();
    InputStream getInputStream() throws IOException;
}
