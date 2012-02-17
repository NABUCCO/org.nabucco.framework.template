/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.template.impl.service.pdf.merge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRq;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRs;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;

/**
 * MergePdfImpl<p/>Merges PDF files.<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-07-04
 */
public class MergePdfImpl extends ServiceSupport implements MergePdf {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MergePdf";

    private static Map<String, String[]> ASPECTS;

    private MergePdfFilesServiceHandler mergePdfFilesServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MergePdfImpl instance. */
    public MergePdfImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.mergePdfFilesServiceHandler = injector.inject(MergePdfFilesServiceHandler.getId());
        if ((this.mergePdfFilesServiceHandler != null)) {
            this.mergePdfFilesServiceHandler.setPersistenceManager(persistenceManager);
            this.mergePdfFilesServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("mergePdfFiles", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<MergePdfRs> mergePdfFiles(ServiceRequest<MergePdfRq> rq) throws TemplateException {
        if ((this.mergePdfFilesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for mergePdfFiles().");
            throw new InjectionException("No service implementation configured for mergePdfFiles().");
        }
        ServiceResponse<MergePdfRs> rs;
        this.mergePdfFilesServiceHandler.init();
        rs = this.mergePdfFilesServiceHandler.invoke(rq);
        this.mergePdfFilesServiceHandler.finish();
        return rs;
    }
}
