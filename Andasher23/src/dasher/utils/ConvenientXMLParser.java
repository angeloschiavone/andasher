package dasher.utils;

import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 *
 * @author joshua
 */
public abstract class ConvenientXMLParser<Result> extends DefaultHandler {

    protected XMLReader reader;
    protected String dtd;
    protected Result result;

    public ConvenientXMLParser() throws SAXException {
        reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(this);
        reader.setEntityResolver(this);
    }

    /**
     * 
     * @param in
     * @return Result
     * @throws IOException
     * @throws SAXException
     */
    public Result parse(InputSource in) throws IOException, SAXException {
        reader.parse(in);
        return result;
    }

    /**
     *
     * @param in
     * @return Result
     * @throws IOException
     * @throws SAXException
     */
    public Result parse(InputStream in) throws IOException, SAXException {
        return parse(new InputSource(in));
    }

    /**
     *
     * @param url
     * @return Result
     * @throws IOException
     * @throws SAXException
     */
    public Result parse(String url) throws IOException, SAXException {
        return parse(new InputSource(url));
    }

    @Override
    public InputSource resolveEntity(String publicName, String systemName) throws IOException, SAXException {
        if (systemName.contains(dtd)) {
            return new InputSource(getDTDEntity());
        } else {
            return null;
        }
    }

    /**
     * 
     * @return an InputStream from which the DTD can be read
     * @throws IOException
     */
    protected abstract InputStream getDTDEntity() throws IOException;
}
