<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'personAuthority.label', default: 'PersonAuthority')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-personAuthority" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-personAuthority" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
%{--            <f:table collection="${personAuthorityList}" />--}%

            <ul>
            <g:each in="${personAuthorityList}" var="pa">
                <li>${pa.person.username} - ${pa.authority.authority}</li>
        </g:each>
            </ul>

            <div class="pagination">
                <g:paginate total="${personAuthorityCount ?: 0}" />
            </div>
        </div>
    </body>
</html>