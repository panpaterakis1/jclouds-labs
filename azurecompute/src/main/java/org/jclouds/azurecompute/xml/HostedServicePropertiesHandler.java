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

import org.jclouds.azurecompute.domain.HostedServiceProperties;
import org.jclouds.http.functions.ParseSax;
import org.xml.sax.SAXException;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.BaseEncoding.base64;
import static org.jclouds.util.SaxUtils.currentOrNull;
import static org.jclouds.util.SaxUtils.equalsOrSuffix;

/**
 * @see <a href="http://msdn.microsoft.com/en-us/library/gg441293" >api</a>
 */
public class HostedServicePropertiesHandler extends
         ParseSax.HandlerForGeneratedRequestWithResult<HostedServiceProperties> {

   protected StringBuilder currentText = new StringBuilder();
   private HostedServiceProperties.Builder<?> builder = HostedServiceProperties.builder();

   @Override
   public HostedServiceProperties getResult() {
      try {
         return builder.build();
      } finally {
         builder = HostedServiceProperties.builder();
      }
   }

   @Override
   public void endElement(String uri, String name, String qName) throws SAXException {
      if (equalsOrSuffix(qName, "Description")) {
         builder.description(currentOrNull(currentText));
      } else if (equalsOrSuffix(qName, "Location")) {
         builder.location(currentOrNull(currentText));
      } else if (equalsOrSuffix(qName, "AffinityGroup")) {
         builder.affinityGroup(currentOrNull(currentText));
      } else if (equalsOrSuffix(qName, "Label")) {
         builder.label(new String(base64().decode(currentOrNull(currentText)), UTF_8));
      }
      currentText.setLength(0);
   }

   @Override
   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }
}
