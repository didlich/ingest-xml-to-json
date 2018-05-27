/*
 * Copyright [2017] [didlich]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.elasticsearch.plugin.ingest.xml.to.json;

import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.RandomDocumentPicks;
import org.elasticsearch.test.ESTestCase;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class XmlToJsonProcessorTests extends ESTestCase {

    public void testThatProcessorWorks() throws Exception {
        Map<String, Object> document = new HashMap<>();

        String TEST_XML_STRING = "<?xml version=\"1.0\" ?><person id=\"4711\">max mustermann</person>";

        String TEST_JSON_STRING = "{\"person\":{\"id\":4711,\"content\":\"max mustermann\"}}";
        document.put("source_field", TEST_XML_STRING);
        IngestDocument ingestDocument = RandomDocumentPicks.randomIngestDocument(random(), document);

        XmlToJsonProcessor processor = new XmlToJsonProcessor(randomAlphaOfLength(10), "source_field", "target_field");
        processor.execute(ingestDocument);
        Map<String, Object> data = ingestDocument.getSourceAndMetadata();

        assertThat(data, hasKey("target_field"));
        assertThat(data.get("target_field"), is(TEST_JSON_STRING));
        // TODO add fancy assertions here
    }
}

