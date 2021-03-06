/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.azurecompute.xml;

import javax.inject.Inject;
import org.jclouds.azurecompute.domain.DetailedHostedServiceProperties;
import org.jclouds.azurecompute.domain.HostedService.Status;
import org.jclouds.date.DateService;
import org.xml.sax.SAXException;

import static org.jclouds.util.SaxUtils.currentOrNull;
import static org.jclouds.util.SaxUtils.equalsOrSuffix;

/**
 * @see <a href="http://msdn.microsoft.com/en-us/library/gg441293" >api</a>
 */
public class DetailedHostedServicePropertiesHandler extends HostedServicePropertiesHandler {

   private final DateService dateService;

   @Inject
   private DetailedHostedServicePropertiesHandler(DateService dateService) {
      this.dateService = dateService;
   }

   private DetailedHostedServiceProperties.Builder builder = DetailedHostedServiceProperties.builder();

   private String name;

   @Override
   public DetailedHostedServiceProperties getResult() {
      try {
         return builder.fromHostedServiceProperties(super.getResult()).build();
      } finally {
         builder = DetailedHostedServiceProperties.builder();
      }
   }

   @Override
   public void endElement(String uri, String name, String qName) throws SAXException {
      if (equalsOrSuffix(qName, "DateCreated")) {
         builder.created(dateService.iso8601SecondsDateParse(currentOrNull(currentText)));
      } else if (equalsOrSuffix(qName, "DateLastModified")) {
         builder.lastModified(dateService.iso8601SecondsDateParse(currentOrNull(currentText)));
      } else if (equalsOrSuffix(qName, "Status")) {
         String rawStatus = currentOrNull(currentText);
         builder.rawStatus(rawStatus);
         builder.status(Status.fromValue(rawStatus));
      } else if (equalsOrSuffix(qName, "Name")) {
         this.name = currentOrNull(currentText);
      } else if (equalsOrSuffix(qName, "Value")) {
         builder.addExtendedProperty(this.name, currentOrNull(currentText));
         this.name = null;
      } else {
         super.endElement(uri, name, qName);
      }
      currentText.setLength(0);
   }
}
