/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.openticket.integrationtest.util;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;

public final class OpenTicketBundles {
    public static final MavenArtifactProvisionOption OPENTICKET_APP = CoreOptions
        .mavenBundle(new MavenArtifactUrlReference().groupId("org.openengsb.openticket").artifactId(
                "openticket-app").type("war").version(new OpenTicketVersionResolver()));

    public static final MavenArtifactProvisionOption OPENENGSB_INTEGRATIONTEST_WRAPPED_HTMLUNIT = CoreOptions
        .mavenBundle(new MavenArtifactUrlReference().groupId("org.openengsb.integrationtest.wrapped")
            .artifactId("net.sourceforge.htmlunit-all").version(new OpenEngSBVersionResolver()));

    private OpenTicketBundles() {
        // should not be instanciable, but should be allowed to contain private
        // methods
    }
}
