/**
 * Copyright (c) 2014 Gang Ling.
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
 *
*/
package org.spdx.merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.spdx.compare.LicenseCompareHelper;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SPDXDocument;
import org.spdx.rdfparser.SPDXNonStandardLicense;

/**
 * Application to merge SPDX documents' non-standard license information and return the results to the merge main class
 * The non-standard license information from the master SPDX document will add to the result arraylist directly.
 * The non-standard license information from the child SPDX document will be compared with license information in the result arraylist.
 * Any new license information will add to the result arraylist with replacing the license ID. 
 * A HashMap will track the changing of license.
 *  
 * @author Gang Ling
 *
 */
public class SpdxLicenseInfoMerger {
	
    /**	
     * @param mergeDocs
     * @param licIdMap
     * @return licInfoResut
     * @throws InvalidSPDXAnalysisException
     */
	public ArrayList<SPDXNonStandardLicense> mergeNonStandardLic(SPDXDocument[] mergeDocs,
					HashMap<SPDXDocument,HashMap<SPDXNonStandardLicense,SPDXNonStandardLicense>> licIdMap) throws InvalidSPDXAnalysisException{
		
		//an array to hold the non-standard license info from master SPDX document
		SPDXNonStandardLicense[] masterNonStandardLicInfo = cloneNonStdLic(mergeDocs[0].getExtractedLicenseInfos());
		
		//an arrayList to hold the final result 
		ArrayList<SPDXNonStandardLicense> licInfoResult = new ArrayList<SPDXNonStandardLicense>(Arrays.asList(masterNonStandardLicInfo));

		//read each child SPDX document
		for(int i = 1; i < mergeDocs.length; i++){
			
			//an array to hold non-standard license info from current child SPDX document and clone the data
			SPDXNonStandardLicense[] childNonStandardLicInfo = cloneNonStdLic(mergeDocs[i].getExtractedLicenseInfos());
			
			//an HashMap to track the changing of license ID from current child SPDX document
			HashMap<SPDXNonStandardLicense, SPDXNonStandardLicense> idMap = new HashMap<SPDXNonStandardLicense, SPDXNonStandardLicense>();
			
			//compare non-standard license info. Note: the method may run as over-comparison
	        for(int k = 0; k < licInfoResult.size(); k++){
	        	boolean foundTextMatch = false;
	        	for(int p = 0; p < childNonStandardLicInfo.length; p++){
	        		if(LicenseCompareHelper.isLicenseTextEquivalent(licInfoResult.get(k).getText(), childNonStandardLicInfo[p].getText())){
	        			foundTextMatch = true;
	           		}
	        		if(!foundTextMatch){
	        		    String newLicId = mergeDocs[0].getNextLicenseRef();
	        	        childNonStandardLicInfo[p].setId(newLicId);
	        	        licInfoResult.add(childNonStandardLicInfo[p]);	        	        
	        			idMap.put(childNonStandardLicInfo[p], childNonStandardLicInfo[p]);        			
	        		}
	        	}
	        }
	        licIdMap.put(mergeDocs[i], idMap);			
		}
		return licInfoResult;
	}
	
	/**
	 * 
	 * @param orgNonStdLicArray
	 * @return clonedNonStdLicArray
	 */
	public SPDXNonStandardLicense[] cloneNonStdLic(SPDXNonStandardLicense[] orgNonStdLicArray){
		SPDXNonStandardLicense[] clonedNonStdLicArray = new SPDXNonStandardLicense[orgNonStdLicArray.length];
		for(int q = 0; q < orgNonStdLicArray.length; q++){
			clonedNonStdLicArray[q] = (SPDXNonStandardLicense) orgNonStdLicArray[q].clone();
		}
		return clonedNonStdLicArray;
	}
}
