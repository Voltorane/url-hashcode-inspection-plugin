package com.github.voltorane.urlhashcodeinspectionplugin

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethodCallExpression

class UrlHashcodeInspection : AbstractBaseJavaLocalInspectionTool() {
    companion object {
        private const val URL_CLASS_QUALIFIED_NAME = "java.net.URL"
        private const val HASH_CODE_METHOD_NAME = "hashCode"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {
            override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
                super.visitMethodCallExpression(expression)
                if (expression != null && expression.methodExpression.referenceName == HASH_CODE_METHOD_NAME) {
                    val method = expression.resolveMethod()
                    if (method?.containingClass?.qualifiedName == URL_CLASS_QUALIFIED_NAME
                        && method.name == HASH_CODE_METHOD_NAME
                    ) {
                        holder.registerProblem(
                            expression,
                            InspectionBundle.message("display.description")
                        )
                    }
                }
            }
        }
    }
}