/*
    Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
    except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
    the specific language governing permissions and limitations under the License.
 */

package com.amazon.ask.response.template.loader.impl;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.response.template.loader.TemplateCache;
import com.amazon.ask.response.template.loader.TemplateEnumerator;

import java.util.function.BiFunction;

/**
 * {@inheritDoc}
 *
 * Use {@link LocaleTemplateEnumerator} as a default {@link TemplateEnumerator} in LocalTemplateFileLoader if no TemplateEnumerator provided.
 */
public class LocalTemplateFileLoader extends AbstractLocalTemplateFileLoader<HandlerInput> {

    /**
     * Constructor for LocalTemplateFileLoader.
     * @param directoryPath Templates' directory path.
     * @param fileExtension Type of templates is determined by this file extension.
     * @param classLoader Loads classes.
     * @param templateCache Caches the template content.
     * @param templateEnumeratorSupplier Template enumerator supplier.
     */
    protected LocalTemplateFileLoader(final String directoryPath, final String fileExtension, final ClassLoader classLoader,
                                      final TemplateCache templateCache,
                                      final BiFunction<String, HandlerInput, TemplateEnumerator<HandlerInput>> templateEnumeratorSupplier) {
        super(directoryPath, fileExtension, classLoader, templateCache, templateEnumeratorSupplier);
    }

    /**
     * Static method to return an instance of Builder.
     * @return {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * LocalTemplateFileLoader Builder.
     */
    public static final class Builder extends AbstractLocalTemplateFileLoader.Builder<HandlerInput, Builder> {

        /**
         * Builder method to build an instance of LocalTemplateFileLoader.
         * @return {@link LocalTemplateFileLoader}.
         */
        public LocalTemplateFileLoader build() {
            return new LocalTemplateFileLoader(directoryPath, fileExtension,
                    classLoader, templateCache,
                    templateEnumeratorSupplier == null
                            ? (BiFunction<String, HandlerInput, TemplateEnumerator<HandlerInput>>) (s, handlerInput) ->
                            LocaleTemplateEnumerator.builder()
                            .withTemplateName(s)
                            .withHandlerInput(handlerInput)
                            .build() : templateEnumeratorSupplier);
        }
    }

}
