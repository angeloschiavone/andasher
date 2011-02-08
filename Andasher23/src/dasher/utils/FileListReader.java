/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dasher.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.angelo.androidprova.resources.StaticResourceManager;

/**
 * Reads from XML list of files into a collection.
 *
 * @author joshua
 */
public class FileListReader extends DefaultHandler {

    protected Collection<String> files;
    protected XMLReader reader;

    /**
     *
     * @param files
     * @throws SAXException
     */
    public FileListReader(Collection<String> files) throws SAXException {
        this.files = files;
        reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(this);
        reader.setEntityResolver(this);
    }

    /**
     *
     * @throws SAXException
     */
    public FileListReader() throws SAXException {
        this(new ArrayList<String>());
    }

    /**
     *
     * @return the collection of files
     */
    public Collection<String> getFiles() {
        return files;
    }

    /**
     *
     * @param in
     * @throws IOException
     * @throws SAXException
     */
    public void parse(InputSource in) throws IOException, SAXException {
        reader.parse(in);
    }

    /**
     *
     * @param in
     * @throws IOException
     * @throws SAXException
     */
    public void parse(InputStream in) throws IOException, SAXException {
        parse(new InputSource(in));
    }

    /**
     *
     * @param sysId
     * @throws IOException
     * @throws SAXException
     */
    public void parse(String sysId) throws IOException, SAXException {
        reader.parse(sysId);
    }

    /**
     * A convienece method for those who do not want to create thier
     * own FileListReader.
     *
     * @param in
     * @param files
     */
    public static void parse(InputStream in, Collection<String> files) throws SAXException, IOException {
        FileListReader reader = new FileListReader(files);
        reader.parse(in);
    }

    @Override
    public void startElement(String namespaceURI, String simpleName, String qualName, Attributes tagAttributes) throws SAXException {
        String tagName = (simpleName.equals("") ? qualName : simpleName);
        if (tagName.equals("file")) {
            // TODO: There must be a better way to get XML attributes!
            for (int i = 0; i < tagAttributes.getLength(); i++) {
                String attributeName = (tagAttributes.getLocalName(i).equals("") ? tagAttributes.getQName(i) : tagAttributes.getLocalName(i));
                if (attributeName.equals("name")) {
                    files.add(tagAttributes.getValue(i));
                }
            }
        }
    }

    @Override
    public InputSource resolveEntity(String publicName, String systemName) throws IOException, SAXException {
        if (systemName.contains("filelist.dtd")) {
            return new InputSource(StaticResourceManager.getResourceStream("filelist.dtd"));
        } else {
            return null;
        }
    }
}
