/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package com.minsait.challenge.twitter.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.minsait.challenge.twitter")
public class LayeredArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()

        .layer("Configuration").definedBy("com.minsait..config..")
        .layer("Controllers").definedBy("com.minsait..controller..")
        .layer("Listeners").definedBy("com.minsait..listener..")
        .layer("Services").definedBy("com.minsait..business..")
        .layer("Persistence").definedBy("com.minsait..persistence..")
        .layer("Persistence Mappers").definedBy("com.minsait..persistence.mapper..")

        .whereLayer("Configuration").mayNotBeAccessedByAnyLayer()
        .whereLayer("Controllers").mayOnlyBeAccessedByLayers("Configuration")
        .whereLayer("Listeners").mayOnlyBeAccessedByLayers("Configuration")
        .whereLayer("Services").mayOnlyBeAccessedByLayers("Configuration", "Controllers", "Listeners")
        .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Configuration", "Services")
        .whereLayer("Persistence Mappers").mayOnlyBeAccessedByLayers("Configuration", "Persistence");

}
