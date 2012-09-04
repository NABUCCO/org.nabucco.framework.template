/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.template.facade.datatype.pdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PdfPrintRequest<p/>A print request for the pdfMerge-service<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-07-05
 */
public class PdfPrintRequest extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String FILENAME = "fileName";

    public static final String NUMBEROFPRINTS = "numberOfPrints";

    public Name fileName;

    public Number numberOfPrints;

    /** Constructs a new PdfPrintRequest instance. */
    public PdfPrintRequest() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PdfPrintRequest.
     */
    protected void cloneObject(PdfPrintRequest clone) {
        super.cloneObject(clone);
        if ((this.getFileName() != null)) {
            clone.setFileName(this.getFileName().cloneObject());
        }
        if ((this.getNumberOfPrints() != null)) {
            clone.setNumberOfPrints(this.getNumberOfPrints().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(FILENAME,
                PropertyDescriptorSupport.createBasetype(FILENAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NUMBEROFPRINTS, PropertyDescriptorSupport.createBasetype(NUMBEROFPRINTS, Number.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PdfPrintRequest.getPropertyDescriptor(FILENAME), this.fileName, null));
        properties.add(super.createProperty(PdfPrintRequest.getPropertyDescriptor(NUMBEROFPRINTS), this.numberOfPrints,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FILENAME) && (property.getType() == Name.class))) {
            this.setFileName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMBEROFPRINTS) && (property.getType() == Number.class))) {
            this.setNumberOfPrints(((Number) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final PdfPrintRequest other = ((PdfPrintRequest) obj);
        if ((this.fileName == null)) {
            if ((other.fileName != null))
                return false;
        } else if ((!this.fileName.equals(other.fileName)))
            return false;
        if ((this.numberOfPrints == null)) {
            if ((other.numberOfPrints != null))
                return false;
        } else if ((!this.numberOfPrints.equals(other.numberOfPrints)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fileName == null) ? 0 : this.fileName.hashCode()));
        result = ((PRIME * result) + ((this.numberOfPrints == null) ? 0 : this.numberOfPrints.hashCode()));
        return result;
    }

    @Override
    public PdfPrintRequest cloneObject() {
        PdfPrintRequest clone = new PdfPrintRequest();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getFileName.
     *
     * @return the Name.
     */
    public Name getFileName() {
        return this.fileName;
    }

    /**
     * Missing description at method setFileName.
     *
     * @param fileName the Name.
     */
    public void setFileName(Name fileName) {
        this.fileName = fileName;
    }

    /**
     * Missing description at method setFileName.
     *
     * @param fileName the String.
     */
    public void setFileName(String fileName) {
        if ((this.fileName == null)) {
            if ((fileName == null)) {
                return;
            }
            this.fileName = new Name();
        }
        this.fileName.setValue(fileName);
    }

    /**
     * Missing description at method getNumberOfPrints.
     *
     * @return the Number.
     */
    public Number getNumberOfPrints() {
        return this.numberOfPrints;
    }

    /**
     * Missing description at method setNumberOfPrints.
     *
     * @param numberOfPrints the Number.
     */
    public void setNumberOfPrints(Number numberOfPrints) {
        this.numberOfPrints = numberOfPrints;
    }

    /**
     * Missing description at method setNumberOfPrints.
     *
     * @param numberOfPrints the Integer.
     */
    public void setNumberOfPrints(Integer numberOfPrints) {
        if ((this.numberOfPrints == null)) {
            if ((numberOfPrints == null)) {
                return;
            }
            this.numberOfPrints = new Number();
        }
        this.numberOfPrints.setValue(numberOfPrints);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PdfPrintRequest.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PdfPrintRequest.class).getAllProperties();
    }
}
