package com.metagxd.solvotest.transfer;

import com.metagxd.solvotest.model.Location;
import com.metagxd.solvotest.util.XMLWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class LocationXMLTransfer implements DataTransfer<Location> {

    private static final Logger logger = LoggerFactory.getLogger(LocationXMLTransfer.class);

    /**
     * Transfer data from database to xml file.
     *
     * @param fileName name of file that will be created or rewritten for store data
     * @param objects list of location that need to transfer at file
     */
    @Override
    public void saveToFile(String fileName, List<Location> objects) throws JAXBException {
        //wrapper for 'dbinfo' tag
        XMLWrapper xmlWrapper = new XMLWrapper(objects);
        try {
            JAXBContext context = JAXBContext.newInstance(XMLWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(xmlWrapper, new File(fileName));
            logger.info("Transfer complete to {}", fileName);
        } catch (JAXBException e) {
            logger.error("An error occurred", e);
            throw e;
        }
    }
}
