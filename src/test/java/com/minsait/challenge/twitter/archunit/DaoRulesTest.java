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
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.sql.SQLException;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "com.minsait.challenge.twitter")
public class DaoRulesTest {

    @ArchTest
    static final ArchRule DAOs_must_reside_in_persistence_package =
            classes()
                    .that().haveNameMatching(".*Dao")
                    .should().resideInAnyPackage("..persistence")
                    .as("DAOs should reside in a package '..persistence'");

    @ArchTest
    static final ArchRule DAOs_Impl_must_reside_in_persistence_impl_package =
            classes()
                    .that().haveNameMatching(".*DaoImpl")
                    .should().resideInAnyPackage("..persistence.impl")
                    .as("DAOs should reside in a package '..persistence.impl'");

    @ArchTest
    static final ArchRule Entities_must_reside_in_persistence_entity_package_suffix =
            classes()
                    .that().haveNameMatching(".*Entity")
                    .should().resideInAnyPackage("..persistence.entities")
                    .as("Entities should reside in a package '..persistence.entities'");

    @ArchTest
    static final ArchRule Entities_must_reside_in_persistence_entity_package_annotation =
            classes()
                    .that().areAnnotatedWith(Entity.class)
                    .should().resideInAnyPackage("..persistence.entities")
                    .as("Entities should reside in a package '..persistence.entities'");

    @ArchTest
    static final ArchRule Repositories_must_reside_in_persistence_repository_package =
            classes()
                    .that().haveNameMatching(".*Repository")
                    .should().resideInAnyPackage("..persistence.repositories")
                    .as("Repositories should reside in a package '..persistence.repositories'");

    @ArchTest
    static final ArchRule only_DAOs_may_use_the_JpaRepository =
            noClasses()
                    .that().resideOutsideOfPackage("..persistence.impl")
                    .should().accessClassesThat().areAssignableTo(JpaRepository.class)
                    .as("Only DAOs may use the " + JpaRepository.class.getSimpleName());

    @ArchTest
    static final ArchRule DAOs_must_not_throw_SQLException =
            noMethods()
                    .that().areDeclaredInClassesThat().haveNameMatching(".*DaoImpl")
                    .should().declareThrowableOfType(SQLException.class);

    @ArchTest
    static final ArchRule only_repositories_should_have_Repository_annotation =
            noClasses()
                    .that().areAnnotatedWith(Repository.class)
                    .should().resideOutsideOfPackage("..repositories")
                    .as("Only Repositories in '..repository' package should have Repository annotation");

    @ArchTest
    static final ArchRule only_mappers_should_have_Mapper_annotation =
            noClasses()
                    .that().areAnnotatedWith(Mapper.class)
                    .should().resideOutsideOfPackages("..persistence.mapper", "..listener.mapper")
                    .as("Only Mappers in '..persistence.mapper' or '..listener.mapper' package should have Mapper annotation");

    @ArchTest
    static final ArchRule only_mappers_should_be_in_mapper_package =
            noClasses()
                    .that().haveNameMatching(".*Mapper(Impl)?")
                    .should().resideOutsideOfPackages("..persistence.mapper", "..listener.mapper")
                    .as("Only Mappers in '..persistence.mapper' or '..listener.mapper' package should have Mapper annotation");

    @ArchTest
    static final ArchRule only_DAOs_should_have_Transactional_annotation =
            noMethods()
                    .that().areAnnotatedWith(Transactional.class)
                    .should().beDeclaredInClassesThat().resideOutsideOfPackage("..persistence")
                    .as("Only DAOs in '..persistence' package should have Transactional annotation");

}
