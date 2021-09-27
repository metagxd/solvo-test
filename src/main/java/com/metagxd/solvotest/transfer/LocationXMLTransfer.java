package com.metagxd.solvotest.transfer;

import com.metagxd.solvotest.model.Location;
import com.metagxd.solvotest.util.XMLWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class LocationXMLTransfer implements DataTransfer<Location> {

    public LocationXMLTransfer() {
    }

    @Override
    public void saveToFile(String fileName, List<Location> objectsList) {
        JAXBContext context = null;
        XMLWrapper xmlWrapper = new XMLWrapper(objectsList);
        try {
            context = JAXBContext.newInstance(XMLWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.marshal(xmlWrapper, new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
