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
package org.nabucco.framework.template.ui.web.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateFactorySupport;
import org.nabucco.framework.template.facade.component.TemplateComponent;
import org.nabucco.framework.template.facade.component.TemplateComponentLocator;
import org.nabucco.framework.template.ui.web.communication.datastructure.maintain.MaintainDatastructureDelegate;
import org.nabucco.framework.template.ui.web.communication.datastructure.produce.ProduceDatastructureDelegate;
import org.nabucco.framework.template.ui.web.communication.datastructure.resolve.ResolveDatastructureDelegate;
import org.nabucco.framework.template.ui.web.communication.datastructure.transfer.TransferDatastructureDataDelegate;
import org.nabucco.framework.template.ui.web.communication.odf.produce.ProduceOdfDelegate;
import org.nabucco.framework.template.ui.web.communication.odf.text.OdtTemplateServiceDelegate;
import org.nabucco.framework.template.ui.web.communication.pdf.merge.MergePdfDelegate;
import org.nabucco.framework.template.ui.web.communication.textmodule.maintain.MaintainTextModuleDelegate;
import org.nabucco.framework.template.ui.web.communication.textmodule.produce.ProduceTextModuleDelegate;
import org.nabucco.framework.template.ui.web.communication.textmodule.resolve.ResolveTextModuleDelegate;
import org.nabucco.framework.template.ui.web.communication.textmodule.search.SearchTextModuleDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public class TemplateComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<TemplateComponent> {

    private static TemplateComponentServiceDelegateFactory instance = new TemplateComponentServiceDelegateFactory();

    private ProduceDatastructureDelegate produceDatastructureDelegate;

    private MaintainDatastructureDelegate maintainDatastructureDelegate;

    private ResolveDatastructureDelegate resolveDatastructureDelegate;

    private TransferDatastructureDataDelegate transferDatastructureDataDelegate;

    private ProduceTextModuleDelegate produceTextModuleDelegate;

    private MaintainTextModuleDelegate maintainTextModuleDelegate;

    private ResolveTextModuleDelegate resolveTextModuleDelegate;

    private SearchTextModuleDelegate searchTextModuleDelegate;

    private ProduceOdfDelegate produceOdfDelegate;

    private MergePdfDelegate mergePdfDelegate;

    private OdtTemplateServiceDelegate odtTemplateServiceDelegate;

    /** Constructs a new TemplateComponentServiceDelegateFactory instance. */
    private TemplateComponentServiceDelegateFactory() {
        super(TemplateComponentLocator.getInstance());
    }

    /**
     * Getter for the ProduceDatastructure.
     *
     * @return the ProduceDatastructureDelegate.
     * @throws ClientException
     */
    public ProduceDatastructureDelegate getProduceDatastructure() throws ClientException {
        try {
            if ((this.produceDatastructureDelegate == null)) {
                this.produceDatastructureDelegate = new ProduceDatastructureDelegate(this.getComponent()
                        .getProduceDatastructure());
            }
            return this.produceDatastructureDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceDatastructure", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the MaintainDatastructure.
     *
     * @return the MaintainDatastructureDelegate.
     * @throws ClientException
     */
    public MaintainDatastructureDelegate getMaintainDatastructure() throws ClientException {
        try {
            if ((this.maintainDatastructureDelegate == null)) {
                this.maintainDatastructureDelegate = new MaintainDatastructureDelegate(this.getComponent()
                        .getMaintainDatastructure());
            }
            return this.maintainDatastructureDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainDatastructure", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ResolveDatastructure.
     *
     * @return the ResolveDatastructureDelegate.
     * @throws ClientException
     */
    public ResolveDatastructureDelegate getResolveDatastructure() throws ClientException {
        try {
            if ((this.resolveDatastructureDelegate == null)) {
                this.resolveDatastructureDelegate = new ResolveDatastructureDelegate(this.getComponent()
                        .getResolveDatastructure());
            }
            return this.resolveDatastructureDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ResolveDatastructure", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the TransferDatastructureData.
     *
     * @return the TransferDatastructureDataDelegate.
     * @throws ClientException
     */
    public TransferDatastructureDataDelegate getTransferDatastructureData() throws ClientException {
        try {
            if ((this.transferDatastructureDataDelegate == null)) {
                this.transferDatastructureDataDelegate = new TransferDatastructureDataDelegate(this.getComponent()
                        .getTransferDatastructureData());
            }
            return this.transferDatastructureDataDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: TransferDatastructureData", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceTextModule.
     *
     * @return the ProduceTextModuleDelegate.
     * @throws ClientException
     */
    public ProduceTextModuleDelegate getProduceTextModule() throws ClientException {
        try {
            if ((this.produceTextModuleDelegate == null)) {
                this.produceTextModuleDelegate = new ProduceTextModuleDelegate(this.getComponent()
                        .getProduceTextModule());
            }
            return this.produceTextModuleDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceTextModule", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the MaintainTextModule.
     *
     * @return the MaintainTextModuleDelegate.
     * @throws ClientException
     */
    public MaintainTextModuleDelegate getMaintainTextModule() throws ClientException {
        try {
            if ((this.maintainTextModuleDelegate == null)) {
                this.maintainTextModuleDelegate = new MaintainTextModuleDelegate(this.getComponent()
                        .getMaintainTextModule());
            }
            return this.maintainTextModuleDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainTextModule", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ResolveTextModule.
     *
     * @return the ResolveTextModuleDelegate.
     * @throws ClientException
     */
    public ResolveTextModuleDelegate getResolveTextModule() throws ClientException {
        try {
            if ((this.resolveTextModuleDelegate == null)) {
                this.resolveTextModuleDelegate = new ResolveTextModuleDelegate(this.getComponent()
                        .getResolveTextModule());
            }
            return this.resolveTextModuleDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ResolveTextModule", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the SearchTextModule.
     *
     * @return the SearchTextModuleDelegate.
     * @throws ClientException
     */
    public SearchTextModuleDelegate getSearchTextModule() throws ClientException {
        try {
            if ((this.searchTextModuleDelegate == null)) {
                this.searchTextModuleDelegate = new SearchTextModuleDelegate(this.getComponent().getSearchTextModule());
            }
            return this.searchTextModuleDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: SearchTextModule", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceOdf.
     *
     * @return the ProduceOdfDelegate.
     * @throws ClientException
     */
    public ProduceOdfDelegate getProduceOdf() throws ClientException {
        try {
            if ((this.produceOdfDelegate == null)) {
                this.produceOdfDelegate = new ProduceOdfDelegate(this.getComponent().getProduceOdf());
            }
            return this.produceOdfDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceOdf", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the MergePdf.
     *
     * @return the MergePdfDelegate.
     * @throws ClientException
     */
    public MergePdfDelegate getMergePdf() throws ClientException {
        try {
            if ((this.mergePdfDelegate == null)) {
                this.mergePdfDelegate = new MergePdfDelegate(this.getComponent().getMergePdf());
            }
            return this.mergePdfDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MergePdf", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the OdtTemplateService.
     *
     * @return the OdtTemplateServiceDelegate.
     * @throws ClientException
     */
    public OdtTemplateServiceDelegate getOdtTemplateService() throws ClientException {
        try {
            if ((this.odtTemplateServiceDelegate == null)) {
                this.odtTemplateServiceDelegate = new OdtTemplateServiceDelegate(this.getComponent()
                        .getOdtTemplateService());
            }
            return this.odtTemplateServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: OdtTemplateService", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the TemplateComponentServiceDelegateFactory.
     */
    public static TemplateComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
