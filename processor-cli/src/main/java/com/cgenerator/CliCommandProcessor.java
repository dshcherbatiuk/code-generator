package com.cgenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("com.cgenerator.Command")
public class CliCommandProcessor extends AbstractProcessor {

    private Elements elementsUtil;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsUtil = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // The variable element which annotated with @PoOptionjo annotation.
        VariableElement varElement = null;

        // The @Option annotation element.
        AnnotationMirror annotationMirror = null;

        try {
            // This returns the variable element that annotated with @Option annotation.
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Option.class)) {
                varElement = (VariableElement) annotatedElement;

                // Getting all the annotation elements from the variable element.
                List<? extends AnnotationMirror> allAnnotationMirrors = varElement.getAnnotationMirrors();
                for (AnnotationMirror aAnnotationMirror : allAnnotationMirrors) {

                    // Make sure the annotation element is belong to Option annotation type.
                    if (aAnnotationMirror.getAnnotationType().toString().equals(Option.class.getName())) {
                        // Found the @Option annotation element.
                        annotationMirror = aAnnotationMirror;

                        // Getting the annotation element values from the @Option annotation element.
                        Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror
                                .getElementValues();
                        // Continue to get the annotation values. Refer to next section.
                        messager.printMessage(Diagnostic.Kind.NOTE, elementValues.toString(), varElement, annotationMirror);
                        break;
                    }
                }

            }
        } catch (Exception e) {
            error(varElement, annotationMirror, e.getMessage());
        }

        return true;
    }

    private void error(VariableElement varElement, AnnotationMirror annotationMirror,
            String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, varElement, annotationMirror);
    }

}
