/**
 * Copyright (c) 2020 Source Auditor Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.spdx.rdfparser.model;

import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.spdx.rdfparser.IModelContainer;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SpdxRdfConstants;

/**
 * This SPDX element represents no assertion as to an actual SPDX element.
 * 
 * This element should only be used on the right hand side of relationships to represent no assertion
 * as to what element the subject is actually related to.
 * 
 * This element has no properties and a fixed ID of "NOASSERTION".
 *  
 * @author Gary O'Neall
 */
public class SpdxNoAssertionElement extends SpdxConstantElement {
	public static final String NOASSERTION_ELEMENT_ID = "NOASSERTION";
	public static final int NOASSERTION_ELEMENT_HASHCODE = 491;
	public static final String NOASSERTION_ELEMENT_URI = SpdxRdfConstants.SPDX_NAMESPACE+SpdxRdfConstants.TERM_ELEMENT_NOASSERTION;
	
	public SpdxNoAssertionElement(IModelContainer container, Node elementNode)
			throws InvalidSPDXAnalysisException {
		super(container, elementNode);
	}
	
	public SpdxNoAssertionElement() {
		super("NOASSERTION", 
				"This is a NOASSERTION element which indicate no assertion is made whether an element is related to this element");
	}
	
	@Override
	public String getId() {
		return NOASSERTION_ELEMENT_ID;
	}
	
	@Override
	public String toString() {
		return SpdxRdfConstants.NOASSERTION_VALUE;
	}
	
	@Override
	public int hashCode() {
		return NOASSERTION_ELEMENT_HASHCODE;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof SpdxNoAssertionElement;
	}
	
	/* (non-Javadoc)
	 * @see org.spdx.rdfparser.model.RdfModelObject#getType(org.apache.jena.rdf.model.Model)
	 */
	@Override
	public Resource getType(Model model) {
		return model.createResource(SpdxRdfConstants.SPDX_NAMESPACE + SpdxRdfConstants.CLASS_SPDX_NOASSERTION_ELEMENT);
	}
	
	@Override
	public SpdxNoAssertionElement clone() {
		return new SpdxNoAssertionElement();
	}
	
	@Override
	public String getUri(IModelContainer modelContainer)
			throws InvalidSPDXAnalysisException {
		return NOASSERTION_ELEMENT_URI;
	}
}
