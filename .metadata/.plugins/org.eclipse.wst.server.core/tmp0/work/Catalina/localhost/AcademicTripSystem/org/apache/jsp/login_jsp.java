/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.98
 * Generated at: 2025-03-10 22:21:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(4);
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Login - Academic Trip System</title>\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css\" rel=\"stylesheet\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/css/styles.css\">\n");
      out.write("    <style>\n");
      out.write("        /* These styles will override the ones in the stylesheet */\n");
      out.write("        .login-background {\n");
      out.write("            background-image: url('https://images.unsplash.com/photo-1594608661623-aa0bd3a69799?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2069&q=80');\n");
      out.write("            background-size: cover;\n");
      out.write("            background-position: center;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .login-overlay {\n");
      out.write("            background-color: rgba(26, 37, 47, 0.9);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .input-icon-wrap {\n");
      out.write("            position: relative;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .input-icon {\n");
      out.write("            position: absolute;\n");
      out.write("            left: 12px;\n");
      out.write("            top: 50%;\n");
      out.write("            transform: translateY(-50%);\n");
      out.write("            color: #6B7280;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .input-with-icon {\n");
      out.write("            padding-left: 40px !important;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .btn-secondary {\n");
      out.write("            background-color: var(--secondary);\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .btn-secondary:hover {\n");
      out.write("            background-color: #d35400;\n");
      out.write("        }\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body class=\"login-background\">\n");
      out.write("    <div class=\"page-wrapper\">\n");
      out.write("        <div class=\"login-overlay main-content flex items-center justify-center min-h-screen py-12 px-4 sm:px-6 lg:px-8\">\n");
      out.write("            <div class=\"max-w-md w-full\">\n");
      out.write("                <div class=\"bg-white shadow-xl rounded-lg overflow-hidden\">\n");
      out.write("                    <!-- Header with logo -->\n");
      out.write("                    <div class=\"bg-primary p-6 text-center\">\n");
      out.write("                        <div class=\"login-logo mb-4\">\n");
      out.write("                            <i class=\"fas fa-bus\"></i>\n");
      out.write("                        </div>\n");
      out.write("                        <h2 class=\"text-xl md:text-2xl font-bold text-white\">Academic Trip System</h2>\n");
      out.write("                        <p class=\"text-blue-100 mt-1\">Educational Journey Management</p>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    <div class=\"p-8\">\n");
      out.write("                        <!-- Status Messages -->\n");
      out.write("                        ");
 String status = request.getParameter("status"); 
      out.write("\n");
      out.write("                        ");
 if (status != null) { 
      out.write("\n");
      out.write("                            ");
 if (status.equals("logout")) { 
      out.write("\n");
      out.write("                                <div class=\"bg-blue-50 border-l-4 border-blue-500 p-4 mb-6\" id=\"statusAlert\">\n");
      out.write("                                    <div class=\"flex\">\n");
      out.write("                                        <div class=\"flex-shrink-0\">\n");
      out.write("                                            <i class=\"fas fa-info-circle text-blue-500\"></i>\n");
      out.write("                                        </div>\n");
      out.write("                                        <div class=\"ml-3\">\n");
      out.write("                                            <p class=\"text-blue-700\">You have been successfully logged out.</p>\n");
      out.write("                                        </div>\n");
      out.write("                                        <button onclick=\"document.getElementById('statusAlert').style.display='none'\" class=\"ml-auto text-blue-500\">\n");
      out.write("                                            <i class=\"fas fa-times\"></i>\n");
      out.write("                                        </button>\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                            ");
 } else if (status.equals("error")) { 
      out.write("\n");
      out.write("                                <div class=\"bg-red-50 border-l-4 border-red-500 p-4 mb-6\" id=\"errorAlert\">\n");
      out.write("                                    <div class=\"flex\">\n");
      out.write("                                        <div class=\"flex-shrink-0\">\n");
      out.write("                                            <i class=\"fas fa-exclamation-circle text-red-500\"></i>\n");
      out.write("                                        </div>\n");
      out.write("                                        <div class=\"ml-3\">\n");
      out.write("                                            <p class=\"text-red-700\">Invalid username or password.</p>\n");
      out.write("                                        </div>\n");
      out.write("                                        <button onclick=\"document.getElementById('errorAlert').style.display='none'\" class=\"ml-auto text-red-500\">\n");
      out.write("                                            <i class=\"fas fa-times\"></i>\n");
      out.write("                                        </button>\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                            ");
 } 
      out.write("\n");
      out.write("                        ");
 } 
      out.write("\n");
      out.write("\n");
      out.write("                        <form class=\"space-y-6\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/LoginServlet\" method=\"post\">\n");
      out.write("                            <div>\n");
      out.write("                                <label for=\"username\" class=\"block text-sm font-medium text-gray-700 mb-2\">Username</label>\n");
      out.write("                                <div class=\"input-icon-wrap\">\n");
      out.write("                                    <i class=\"fas fa-user input-icon\"></i>\n");
      out.write("                                    <input id=\"username\" name=\"username\" type=\"text\" autocomplete=\"username\" required \n");
      out.write("                                        class=\"form-input input-with-icon w-full border-gray-300 rounded-md shadow-sm \n");
      out.write("                                        focus:ring-primary focus:border-primary focus:ring-2 transition-all\" \n");
      out.write("                                        placeholder=\"Enter your username\">\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                            <div>\n");
      out.write("                                <label for=\"password\" class=\"block text-sm font-medium text-gray-700 mb-2\">Password</label>\n");
      out.write("                                <div class=\"input-icon-wrap\">\n");
      out.write("                                    <i class=\"fas fa-lock input-icon\"></i>\n");
      out.write("                                    <input id=\"password\" name=\"password\" type=\"password\" autocomplete=\"current-password\" required \n");
      out.write("                                        class=\"form-input input-with-icon w-full border-gray-300 rounded-md shadow-sm\n");
      out.write("                                        focus:ring-primary focus:border-primary focus:ring-2 transition-all\" \n");
      out.write("                                        placeholder=\"Enter your password\">\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                            <div class=\"flex items-center justify-between\">\n");
      out.write("                                <div class=\"flex items-center\">\n");
      out.write("                                    <input id=\"remember-me\" name=\"remember-me\" type=\"checkbox\" \n");
      out.write("                                        class=\"h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded\">\n");
      out.write("                                    <label for=\"remember-me\" class=\"ml-2 block text-sm text-gray-900\">\n");
      out.write("                                        Remember me\n");
      out.write("                                    </label>\n");
      out.write("                                </div>\n");
      out.write("\n");
      out.write("                                <div class=\"text-sm\">\n");
      out.write("                                    <a href=\"#\" class=\"font-medium text-secondary hover:text-primary-dark transition-colors\">\n");
      out.write("                                        Forgot password?\n");
      out.write("                                    </a>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                            <div>\n");
      out.write("                                <button type=\"submit\" class=\"btn btn-secondary w-full flex justify-center py-3 px-4 rounded-md shadow-md hover:shadow-lg transition-all\">\n");
      out.write("                                    <span class=\"mr-2\">Sign in</span>\n");
      out.write("                                    <i class=\"fas fa-sign-in-alt\"></i>\n");
      out.write("                                </button>\n");
      out.write("                            </div>\n");
      out.write("                        </form>\n");
      out.write("                        \n");
      out.write("                        <div class=\"mt-6 text-center text-sm\">\n");
      out.write("                            <p class=\"text-gray-600\">\n");
      out.write("                                For access issues, please contact your system administrator\n");
      out.write("                            </p>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                \n");
      out.write("                <div class=\"mt-4 text-center text-xs text-white\">\n");
      out.write("                    <p>© 2023 Academic Trip System. All rights reserved.</p>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        // Auto-hide alerts after 5 seconds\n");
      out.write("        window.onload = function() {\n");
      out.write("            setTimeout(function() {\n");
      out.write("                const alerts = document.querySelectorAll('#statusAlert, #errorAlert');\n");
      out.write("                alerts.forEach(function(alert) {\n");
      out.write("                    if (alert) alert.style.display = 'none';\n");
      out.write("                });\n");
      out.write("            }, 5000);\n");
      out.write("            \n");
      out.write("            // Add focus animation\n");
      out.write("            const inputs = document.querySelectorAll('input[type=\"text\"], input[type=\"password\"]');\n");
      out.write("            inputs.forEach(input => {\n");
      out.write("                input.addEventListener('focus', function() {\n");
      out.write("                    this.parentNode.querySelector('.input-icon').style.color = '#e67e22';\n");
      out.write("                });\n");
      out.write("                \n");
      out.write("                input.addEventListener('blur', function() {\n");
      out.write("                    this.parentNode.querySelector('.input-icon').style.color = '#6B7280';\n");
      out.write("                });\n");
      out.write("            });\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
