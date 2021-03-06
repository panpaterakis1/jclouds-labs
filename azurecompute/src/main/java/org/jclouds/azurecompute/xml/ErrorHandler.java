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

import org.jclouds.azurecompute.domain.Error;
import org.jclouds.azurecompute.domain.Error.Code;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.util.SaxUtils;
import org.xml.sax.SAXException;

/**
 * @see <a href="http://msdn.microsoft.com/en-us/library/ee460801" >api</a>
 */
public class ErrorHandler extends ParseSax.HandlerForGeneratedRequestWithResult<Error> {

   private StringBuilder currentText = new StringBuilder();
   private Error.Builder builder = Error.builder();

   @Override
   public Error getResult() {
      try {
         return builder.build();
      } finally {
         builder = Error.builder();
      }
   }

   @Override
   public void endElement(String uri, String name, String qName) throws SAXException {
      if (qName.equals("Code")) {
         String rawCode = SaxUtils.currentOrNull(currentText);
         builder.rawCode(rawCode);
         builder.code(Code.fromValue(rawCode));
      } else if (qName.equals("Message")) {
         builder.message(SaxUtils.currentOrNull(currentText));
      }
      currentText.setLength(0);
   }

   @Override
   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }
}
