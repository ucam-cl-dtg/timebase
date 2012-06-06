package uk.ac.cam.cl.dtg.android.time.data.handlers;

public interface SAXDataHandler<T> extends org.xml.sax.ContentHandler {

  public T getData();
}
