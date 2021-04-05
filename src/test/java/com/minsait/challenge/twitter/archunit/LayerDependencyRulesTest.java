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

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.minsait.challenge.twitter")
public class LayerDependencyRulesTest {

    @ArchTest
    static final ArchRule services_should_not_access_controllers =
            noClasses()
                    .that().resideInAPackage("..business..")
                    .should().accessClassesThat().resideInAPackage("..controller..")
                    .as("Services should not access controllers");

    @ArchTest
    static final ArchRule only_controllers_may_use_business_services =
            noClasses()
                    .that().resideOutsideOfPackages("..controller", "..listener", "..business")
                    .should().accessClassesThat().resideInAnyPackage("..business")
                    .as("Only controllers, listeners or business may use business services");

    @ArchTest
    static final ArchRule persistence_should_not_access_services =
            noClasses()
                    .that().resideInAPackage("..persistence..")
                    .should().accessClassesThat().resideInAPackage("..business..")
                    .as("Persistence classes should not access business services");

    @ArchTest
    static final ArchRule only_mappers_should_only_be_accessed_by_daos =
            classes()
                    .that().resideInAPackage("..persistence.mapper")
                    .should().onlyBeAccessed().byAnyPackage("..persistence.impl", "..business.impl", "..persistence.mapper")
                    .as("Persistence mappers should only be accessed by DAO or business implementations");

    @ArchTest
    static final ArchRule services_should_only_be_accessed_by_controllers_or_other_services =
            classes()
                    .that().resideInAPackage("..business..")
                    .should().onlyBeAccessed().byAnyPackage("..controller..", "..listener..", "..business..")
                    .as("Services should only be accessed by controllers, listeners or other services");

    @ArchTest
    static final ArchRule services_should_only_access_persistence_or_other_services =
            classes()
                    .that().resideInAPackage("..business..")
                    .should().onlyAccessClassesThat()
                        .resideInAnyPackage("..business..",
                                            "..persistence..",
                                            "..exception",
                                            "..util..",
                                            "..domain",
                                            "twitter4j..",
                                            "java..",
                                            "org.springframework..",
                                            "org.slf4j..",
                                            "groovy..",
                                            "org.codehaus.groovy..",
                                            "org.spockframework..",
                                            "spock.lang..");

    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers =
            noClasses()
                    .that().resideInAPackage("..business..")
                    .should().dependOnClassesThat().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule persistence_should_not_depend_on_services =
            noClasses()
                    .that().resideInAPackage("..persistence..")
                    .should().dependOnClassesThat().resideInAPackage("..business..");

    @ArchTest
    static final ArchRule services_should_only_be_depended_on_by_controllers_or_other_services =
            classes()
                    .that().resideInAPackage("..business..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..controller..", "..business..", "..listener..", "..config..");

    @ArchTest
    static final ArchRule services_should_only_depend_on_persistence_or_other_services =
            classes().that().resideInAPackage("..business..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..business..",
                                                                           "..persistence..",
                                                                           "..util..",
                                                                           "..exception",
                                                                           "..domain",
                                                                           "twitter4j..",
                                                                           "java..",
                                                                           "org.springframework..",
                                                                           "org.slf4j..",
                                                                           "groovy..",
                                                                           "org.codehaus.groovy..",
                                                                           "org.spockframework..",
                                                                           "spock.lang..");

}
