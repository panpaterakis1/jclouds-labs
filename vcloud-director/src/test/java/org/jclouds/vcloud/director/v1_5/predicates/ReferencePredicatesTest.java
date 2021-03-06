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
package org.jclouds.vcloud.director.v1_5.predicates;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

@Test(groups = "unit", testName = "ReferencePredicatesTest")
public class ReferencePredicatesTest {
   Reference ref = Reference.builder().type("application/vnd.vmware.vcloud.catalogItem+xml").name("image").href(
            URI.create("https://vcloudbeta.bluelock.com/api/catalogItem/67a469a1-aafe-4b5b-bb31-a6202ad8961f")).build();

   @Test
   public void testNameEqualsWhenEqual() {
      assertTrue(ReferencePredicates.<Reference> nameEquals("image").apply(ref));
   }

   @Test
   public void testNameEqualsWhenNotEqual() {
      assertFalse(ReferencePredicates.<Reference> nameEquals("foo").apply(ref));
   }

   @Test
   public void testNameStartsWithWhenStartsWith() {
      assertTrue(ReferencePredicates.<Reference> nameStartsWith("i").apply(ref));
   }

   @Test
   public void testNameStartsWithWhenNotStartsWith() {
      assertFalse(ReferencePredicates.<Reference> nameStartsWith("f").apply(ref));
   }

   @Test
   public void testNameInWhenIn() {
      assertTrue(ReferencePredicates.<Reference> nameIn(ImmutableSet.of("one", "two", "image")).apply(ref));
   }

   @Test
   public void testNameInWhenNotIn() {
      assertFalse(ReferencePredicates.<Reference> nameIn(ImmutableSet.of("one", "two", "foo")).apply(ref));
   }

   @Test
   public void testTypeEqualsWhenEqual() {
      assertTrue(ReferencePredicates.<Reference> typeEquals(VCloudDirectorMediaType.CATALOG_ITEM).apply(ref));
   }

   @Test
   public void testTypeEqualsWhenNotEqual() {
      assertFalse(ReferencePredicates.<Reference> typeEquals("foo").apply(ref));
   }
}
