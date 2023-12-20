<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:saxon="http://saxon.sf.net/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:schold="http://www.ascc.net/xml/schematron"
                xmlns:iso="http://purl.oclc.org/dsdl/schematron"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                version="2.0"><!--Importing stylesheet additions-->
   <xsl:output xmlns:sch="http://www.ascc.net/xml/schematron" method="text"/>
   <!--Implementers: please note that overriding process-prolog or process-root is 
    the preferred method for meta-stylesheets to use where possible. -->
   <xsl:param name="archiveDirParameter"/>
   <xsl:param name="archiveNameParameter"/>
   <xsl:param name="fileNameParameter"/>
   <xsl:param name="fileDirParameter"/>
   <xsl:variable name="document-uri">
      <xsl:value-of select="document-uri(/)"/>
   </xsl:variable>

   <!--PHASES-->


   <!--PROLOG-->


   <!--XSD TYPES FOR XSLT2-->


   <!--KEYS AND FUNCTIONS-->
   <xsl:key name="xrefs"
            match="*[@linkend,@linkends]"
            use="@linkend,tokenize(@linkends,'\s+')"/>

   <!--DEFAULT RULES-->


   <!--MODE: SCHEMATRON-SELECT-FULL-PATH-->
   <!--This mode can be used to generate an ugly though full XPath for locators-->
   <xsl:template match="*" mode="schematron-select-full-path">
      <xsl:apply-templates select="." mode="schematron-get-full-path"/>
   </xsl:template>

   <!--MODE: SCHEMATRON-FULL-PATH-->
   <!--This mode can be used to generate an ugly though full XPath for locators-->
   <xsl:template match="*" mode="schematron-get-full-path">
      <xsl:apply-templates select="parent::*" mode="schematron-get-full-path"/>
      <xsl:text>/</xsl:text>
      <xsl:choose>
         <xsl:when test="namespace-uri()=''">
            <xsl:value-of select="name()"/>
         </xsl:when>
         <xsl:otherwise>
            <xsl:text>*:</xsl:text>
            <xsl:value-of select="local-name()"/>
            <xsl:text>[namespace-uri()='</xsl:text>
            <xsl:value-of select="namespace-uri()"/>
            <xsl:text>']</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:variable name="preceding"
                    select="count(preceding-sibling::*[local-name()=local-name(current())                                   and namespace-uri() = namespace-uri(current())])"/>
      <xsl:text>[</xsl:text>
      <xsl:value-of select="1+ $preceding"/>
      <xsl:text>]</xsl:text>
   </xsl:template>
   <xsl:template match="@*" mode="schematron-get-full-path">
      <xsl:apply-templates select="parent::*" mode="schematron-get-full-path"/>
      <xsl:text>/</xsl:text>
      <xsl:choose>
         <xsl:when test="namespace-uri()=''">@<xsl:value-of select="name()"/>
         </xsl:when>
         <xsl:otherwise>
            <xsl:text>@*[local-name()='</xsl:text>
            <xsl:value-of select="local-name()"/>
            <xsl:text>' and namespace-uri()='</xsl:text>
            <xsl:value-of select="namespace-uri()"/>
            <xsl:text>']</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>

   <!--MODE: SCHEMATRON-FULL-PATH-2-->
   <!--This mode can be used to generate prefixed XPath for humans-->
   <xsl:template match="node() | @*" mode="schematron-get-full-path-2">
      <xsl:for-each select="ancestor-or-self::*">
         <xsl:text>/</xsl:text>
         <xsl:value-of select="name(.)"/>
         <xsl:if test="preceding-sibling::*[name(.)=name(current())]">
            <xsl:text>[</xsl:text>
            <xsl:value-of select="count(preceding-sibling::*[name(.)=name(current())])+1"/>
            <xsl:text>]</xsl:text>
         </xsl:if>
      </xsl:for-each>
      <xsl:if test="not(self::*)">
         <xsl:text/>/@<xsl:value-of select="name(.)"/>
      </xsl:if>
   </xsl:template>
   <!--MODE: SCHEMATRON-FULL-PATH-3-->
   <!--This mode can be used to generate prefixed XPath for humans 
	(Top-level element has index)-->
   <xsl:template match="node() | @*" mode="schematron-get-full-path-3">
      <xsl:for-each select="ancestor-or-self::*">
         <xsl:text>/</xsl:text>
         <xsl:value-of select="name(.)"/>
         <xsl:if test="parent::*">
            <xsl:text>[</xsl:text>
            <xsl:value-of select="count(preceding-sibling::*[name(.)=name(current())])+1"/>
            <xsl:text>]</xsl:text>
         </xsl:if>
      </xsl:for-each>
      <xsl:if test="self::attribute()">
         <xsl:text/>/@<xsl:value-of select="name(.)"/>
      </xsl:if>
      <xsl:if test="self::text()">
         <xsl:text/>/text()"/&gt;
		</xsl:if>
      <xsl:if test="self::processing-instruction()">
         <xsl:text/>/processing-instruction(<xsl:value-of select="name(.)"/>
         <xsl:text>)</xsl:text>
      </xsl:if>
   </xsl:template>

   <!--MODE: GENERATE-ID-FROM-PATH -->
   <xsl:template match="/" mode="generate-id-from-path"/>
   <xsl:template match="text()" mode="generate-id-from-path">
      <xsl:apply-templates select="parent::*" mode="generate-id-from-path"/>
      <xsl:value-of select="concat('.text-', 1+count(preceding-sibling::text()), '-')"/>
   </xsl:template>
   <xsl:template match="comment()" mode="generate-id-from-path">
      <xsl:apply-templates select="parent::*" mode="generate-id-from-path"/>
      <xsl:value-of select="concat('.comment-', 1+count(preceding-sibling::comment()), '-')"/>
   </xsl:template>
   <xsl:template match="processing-instruction()" mode="generate-id-from-path">
      <xsl:apply-templates select="parent::*" mode="generate-id-from-path"/>
      <xsl:value-of select="concat('.processing-instruction-', 1+count(preceding-sibling::processing-instruction()), '-')"/>
   </xsl:template>
   <xsl:template match="@*" mode="generate-id-from-path">
      <xsl:apply-templates select="parent::*" mode="generate-id-from-path"/>
      <xsl:value-of select="concat('.@', name())"/>
   </xsl:template>
   <xsl:template match="*" mode="generate-id-from-path" priority="-0.5">
      <xsl:apply-templates select="parent::*" mode="generate-id-from-path"/>
      <xsl:text>.</xsl:text>
      <xsl:value-of select="concat('.',name(),'-',1+count(preceding-sibling::*[name()=name(current())]),'-')"/>
   </xsl:template>

   <!--MODE: GENERATE-ID-2 -->
   <xsl:template match="/" mode="generate-id-2">U</xsl:template>
   <xsl:template match="*" mode="generate-id-2" priority="2">
      <xsl:text>U</xsl:text>
      <xsl:number level="multiple" count="*"/>
   </xsl:template>
   <xsl:template match="node()" mode="generate-id-2">
      <xsl:text>U.</xsl:text>
      <xsl:number level="multiple" count="*"/>
      <xsl:text>n</xsl:text>
      <xsl:number count="node()"/>
   </xsl:template>
   <xsl:template match="@*" mode="generate-id-2">
      <xsl:text>U.</xsl:text>
      <xsl:number level="multiple" count="*"/>
      <xsl:text>_</xsl:text>
      <xsl:value-of select="string-length(local-name(.))"/>
      <xsl:text>_</xsl:text>
      <xsl:value-of select="translate(name(),':','.')"/>
   </xsl:template>
   <!--Strip characters-->
   <xsl:template match="text()" priority="-1"/>

   <!--SCHEMA SETUP-->
   <xsl:template match="/"><!--Root node processing collects all assertions-->
      <xsl:variable xmlns:sch="http://www.ascc.net/xml/schematron" name="result">
         <xsl:apply-templates select="/" mode="M1"/>
         <xsl:apply-templates select="/" mode="M2"/>
         <xsl:apply-templates select="/" mode="M3"/>
         <xsl:apply-templates select="/" mode="M4"/>
         <xsl:apply-templates select="/" mode="M5"/>
         <xsl:apply-templates select="/" mode="M6"/>
         <xsl:apply-templates select="/" mode="M7"/>
         <xsl:apply-templates select="/" mode="M8"/>
         <xsl:apply-templates select="/" mode="M9"/>
         <xsl:apply-templates select="/" mode="M10"/>
         <xsl:apply-templates select="/" mode="M11"/>
         <xsl:apply-templates select="/" mode="M12"/>
         <xsl:apply-templates select="/" mode="M13"/>
         <xsl:apply-templates select="/" mode="M14"/>
         <xsl:apply-templates select="/" mode="M15"/>
         <xsl:apply-templates select="/" mode="M16"/>
         <xsl:apply-templates select="/" mode="M17"/>
         <xsl:apply-templates select="/" mode="M18"/>
         <xsl:apply-templates select="/" mode="M19"/>
         <xsl:apply-templates select="/" mode="M20"/>
         <xsl:apply-templates select="/" mode="M21"/>
         <xsl:apply-templates select="/" mode="M22"/>
         <xsl:apply-templates select="/" mode="M23"/>
         <xsl:apply-templates select="/" mode="M24"/>
         <xsl:apply-templates select="/" mode="M25"/>
         <xsl:apply-templates select="/" mode="M26"/>
         <xsl:apply-templates select="/" mode="M27"/>
         <xsl:apply-templates select="/" mode="M28"/>
         <xsl:apply-templates select="/" mode="M30"/>
         <xsl:apply-templates select="/" mode="M32"/>
         <xsl:apply-templates select="/" mode="M34"/>
      </xsl:variable>
      <xsl:if xmlns:sch="http://www.ascc.net/xml/schematron"
              test="normalize-space(string($result))">
         <xsl:message terminate="yes">
            <xsl:value-of select="string($result)" disable-output-escaping="yes"/>
         </xsl:message>
      </xsl:if>
   </xsl:template>

   <!--SCHEMATRON PATTERNS-->


   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="section[section]|appendix[section]"
                 priority="1000"
                 mode="M1">

		<!--REPORT -->
      <xsl:if test="* except (section,title) and not(@conformance='skip')">Hanging content is not allowed<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M1"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M1"/>
   <xsl:template match="@*|node()" priority="-2" mode="M1">
      <xsl:apply-templates select="*" mode="M1"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="para" priority="1000" mode="M2">

		<!--REPORT -->
      <xsl:if test="matches(node()[1]/self::text(),'^\s') and                     normalize-space(node()[1]/self::text())">Leading space in a paragraph<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>

		    <!--REPORT -->
      <xsl:if test="not(normalize-space(.)) and not(*) and                     not(@conformance='skip')">An empty paragraph is indicative of an editing oversight<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M2"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M2"/>
   <xsl:template match="@*|node()" priority="-2" mode="M2">
      <xsl:apply-templates select="*" mode="M2"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="ulink[starts-with(@url,'http:') and                          contains(@url,'oasis-open.org')]                         [not(@conformance='skip')]"
                 priority="1000"
                 mode="M3">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="starts-with(@url,'https:')"/>
         <xsl:otherwise>References to OASIS web sites must use https where possible<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M3"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M3"/>
   <xsl:template match="@*|node()" priority="-2" mode="M3">
      <xsl:apply-templates select="*" mode="M3"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="ulink[normalize-space(.)][not(@conformance='skip')]"
                 priority="1000"
                 mode="M4">

		<!--REPORT -->
      <xsl:if test="not(@url=.)">The URI must match the text content.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M4"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M4"/>
   <xsl:template match="@*|node()" priority="-2" mode="M4">
      <xsl:apply-templates select="*" mode="M4"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="text()" priority="1001" mode="M5">

		<!--REPORT -->
      <xsl:if test="contains(.,'﻿')">BOM character found in text<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      
   </xsl:template>

	  <!--RULE -->
   <xsl:template match="@*" priority="1000" mode="M5">

		<!--REPORT -->
      <xsl:if test="contains(.,'﻿')">BOM character found in attribute "<xsl:text/>
         <xsl:value-of select="name(.)"/>
         <xsl:text/>"<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M5"/>
   <xsl:template match="@*|node()" priority="-2" mode="M5">
      <xsl:apply-templates select="*" mode="M5"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="bibliomixed[not(@conformance='skip')]"
                 priority="1000"
                 mode="M6">

		<!--REPORT -->
      <xsl:if test="not(@id)">Every bibliographic entry must be referenceable using an identifier<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>

		    <!--REPORT -->
      <xsl:if test="not(key('xrefs',@id))">Every bibliographic entry must be referenced (or skipped)<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M6"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M6"/>
   <xsl:template match="@*|node()" priority="-2" mode="M6">
      <xsl:apply-templates select="*" mode="M6"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M7">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='doc-sdo'])"/>
         <xsl:otherwise>releaseinfo[@role='doc-sdo'] required for doc-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M7"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M7"/>
   <xsl:template match="@*|node()" priority="-2" mode="M7">
      <xsl:apply-templates select="*" mode="M7"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M8">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='doc-proj-id'])"/>
         <xsl:otherwise>releaseinfo[@role='doc-proj-id'] required for doc-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M8"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M8"/>
   <xsl:template match="@*|node()" priority="-2" mode="M8">
      <xsl:apply-templates select="*" mode="M8"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M9">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='doc-language'])"/>
         <xsl:otherwise>releaseinfo[@role='doc-language'] required for doc-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M9"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M9"/>
   <xsl:template match="@*|node()" priority="-2" mode="M9">
      <xsl:apply-templates select="*" mode="M9"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M10">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='doc-release-version'])"/>
         <xsl:otherwise>releaseinfo[@role='doc-release-version'] required for doc-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M10"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M10"/>
   <xsl:template match="@*|node()" priority="-2" mode="M10">
      <xsl:apply-templates select="*" mode="M10"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M11">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-originator'])"/>
         <xsl:otherwise>releaseinfo[@role='std-originator'] required for std-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M11"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M11"/>
   <xsl:template match="@*|node()" priority="-2" mode="M11">
      <xsl:apply-templates select="*" mode="M11"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M12">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-doc-type'])"/>
         <xsl:otherwise>releaseinfo[@role='std-doc-type'] required for std-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M12"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M12"/>
   <xsl:template match="@*|node()" priority="-2" mode="M12">
      <xsl:apply-templates select="*" mode="M12"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M13">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-doc-number'])"/>
         <xsl:otherwise>releaseinfo[@role='std-doc-number'] required for std-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M13"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M13"/>
   <xsl:template match="@*|node()" priority="-2" mode="M13">
      <xsl:apply-templates select="*" mode="M13"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M14">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-edition'])"/>
         <xsl:otherwise>releaseinfo[@role='std-edition'] required for std-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M14"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M14"/>
   <xsl:template match="@*|node()" priority="-2" mode="M14">
      <xsl:apply-templates select="*" mode="M14"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M15">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-version'])"/>
         <xsl:otherwise>releaseinfo[@role='std-version'] required for std-ident element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M15"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M15"/>
   <xsl:template match="@*|node()" priority="-2" mode="M15">
      <xsl:apply-templates select="*" mode="M15"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M16">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-ref-dated'])"/>
         <xsl:otherwise>releaseinfo[@role='std-ref-dated'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M16"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M16"/>
   <xsl:template match="@*|node()" priority="-2" mode="M16">
      <xsl:apply-templates select="*" mode="M16"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M17">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='std-ref-undated'])"/>
         <xsl:otherwise>releaseinfo[@role='std-ref-undated'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M17"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M17"/>
   <xsl:template match="@*|node()" priority="-2" mode="M17">
      <xsl:apply-templates select="*" mode="M17"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M18">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='doc-ref'])"/>
         <xsl:otherwise>releaseinfo[@role='doc-ref'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M18"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M18"/>
   <xsl:template match="@*|node()" priority="-2" mode="M18">
      <xsl:apply-templates select="*" mode="M18"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M19">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='release-date'])"/>
         <xsl:otherwise>releaseinfo[@role='release-date'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M19"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M19"/>
   <xsl:template match="@*|node()" priority="-2" mode="M19">
      <xsl:apply-templates select="*" mode="M19"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M20">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='comm-ref'])"/>
         <xsl:otherwise>releaseinfo[@role='comm-ref'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M20"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M20"/>
   <xsl:template match="@*|node()" priority="-2" mode="M20">
      <xsl:apply-templates select="*" mode="M20"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M21">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='secretariat'])"/>
         <xsl:otherwise>releaseinfo[@role='secretariat'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M21"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M21"/>
   <xsl:template match="@*|node()" priority="-2" mode="M21">
      <xsl:apply-templates select="*" mode="M21"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[releaseinfo[@role=       ('doc-sdo','doc-proj-id','doc-language','doc-release-version',        'std-originator','std-doc-type','std-doc-number','std-edition',        'std-version','std-ref-dated','std-ref-undated',        'doc-ref','release-date','comm-ref','secretariat','page-count')]]"
                 priority="1000"
                 mode="M22">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(releaseinfo[@role='page-count'])"/>
         <xsl:otherwise>releaseinfo[@role='page-count'] required for iso-meta element<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M22"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M22"/>
   <xsl:template match="@*|node()" priority="-2" mode="M22">
      <xsl:apply-templates select="*" mode="M22"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="articleinfo[/article/articleinfo/releaseinfo/@role='doc-sdo']/copyright"
                 priority="1001"
                 mode="M23">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(year)"/>
         <xsl:otherwise>The copyright year is required by ISO.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M23"/>
   </xsl:template>

	  <!--RULE -->
   <xsl:template match="articleinfo[/article/articleinfo/releaseinfo/@role='doc-sdo']/copyright"
                 priority="1000"
                 mode="M23">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(holder[not(@role)])"/>
         <xsl:otherwise>The copyright holder (no @role attribute) is required by ISO.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M23"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M23"/>
   <xsl:template match="@*|node()" priority="-2" mode="M23">
      <xsl:apply-templates select="*" mode="M23"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="variablelist[/article/articleinfo/releaseinfo/@role='doc-sdo']/varlistentry"
                 priority="1000"
                 mode="M24">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="exists(term[not(@role) or                      @role=('preferredTerm','admittedTerm','deprecatedTerm')])"/>
         <xsl:otherwise>Every varlistentry must define a term with one or more groups of terms starting with either a default (no @role) or @role as one of 'preferredTerm', 'admittedTerm' or 'deprecatedTerm'.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>

		    <!--ASSERT -->
      <xsl:choose>
         <xsl:when test="every $term in term[not(@role) or                      @role=('preferredTerm','admittedTerm','deprecatedTerm')]                     satisfies                      count( $term/following-sibling::term[@role='partOfSpeech']                                             [.=('noun','verb','adj','adv')] ) =                     1 + count( $term/following-sibling::term[not(@role) or                      @role=('preferredTerm','admittedTerm','deprecatedTerm')])"/>
         <xsl:otherwise>Every varlistentry group of terms defined by default (no @role) or @role as one of 'preferredTerm', 'admittedTerm' or 'deprecatedTerm' must also have @role='partOfSpeech' with a value of: "noun" or "verb" or "adj" or "adv".<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M24"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M24"/>
   <xsl:template match="@*|node()" priority="-2" mode="M24">
      <xsl:apply-templates select="*" mode="M24"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="ulink[/article/articleinfo/releaseinfo/@role='doc-sdo']"
                 priority="1000"
                 mode="M25">

		<!--REPORT -->
      <xsl:if test="exists(.//literal)">DocBook2ISOSTS translation prohibits a ulink element to have a literal element as a descendant<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M25"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M25"/>
   <xsl:template match="@*|node()" priority="-2" mode="M25">
      <xsl:apply-templates select="*" mode="M25"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="ulink[/article/articleinfo/releaseinfo/@role='doc-sdo']"
                 priority="1000"
                 mode="M26">

		<!--REPORT -->
      <xsl:if test="exists(emphasis)">DocBook2ISOSTS translation prohibits a ulink element to have an emphasis element as a child<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M26"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M26"/>
   <xsl:template match="@*|node()" priority="-2" mode="M26">
      <xsl:apply-templates select="*" mode="M26"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="para" priority="1000" mode="M27">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="empty(.//itemizedlist | .//orderedlist)"/>
         <xsl:otherwise>A list is not allowed to be inside of a paragraph.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>

		    <!--ASSERT -->
      <xsl:choose>
         <xsl:when test="empty(.//example)"/>
         <xsl:otherwise>An example is not allowed to be inside of a paragraph.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M27"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M27"/>
   <xsl:template match="@*|node()" priority="-2" mode="M27">
      <xsl:apply-templates select="*" mode="M27"/>
   </xsl:template>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="para" priority="1000" mode="M28">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="empty(.//blockquote)"/>
         <xsl:otherwise>A blockquote is not allowed to be inside of a paragraph.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:apply-templates select="*" mode="M28"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M28"/>
   <xsl:template match="@*|node()" priority="-2" mode="M28">
      <xsl:apply-templates select="*" mode="M28"/>
   </xsl:template>
   <xsl:param name="ids"
              select="some $token in tokenize(/*/@conformance,'\s+')               satisfies lower-case($token)='ids'"/>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="section[not(@conformance='skip')][$ids]|                    appendix[not(@conformance='skip')][$ids]|                    table[not(@conformance='skip')][$ids]|                    figure[not(@conformance='skip')][$ids]"
                 priority="1000"
                 mode="M30">

		<!--ASSERT -->
      <xsl:choose>
         <xsl:when test="@id"/>
         <xsl:otherwise>The id= identifier is required.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
            <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                                 select="."
                                 mode="schematron-get-full-path-3"/>
            <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
         </xsl:otherwise>
      </xsl:choose>

		    <!--REPORT -->
      <xsl:if test="@id != concat(upper-case(substring(name(.),1,1)),'-',                 replace(translate(       upper-case(normalize-space((title/phrase[@condition='oasis'],title)[1])),                           ' /=','--'),'[^-A-Z\d.]',''))">The id= identifier must match the title.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      <xsl:apply-templates select="*" mode="M30"/>
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M30"/>
   <xsl:template match="@*|node()" priority="-2" mode="M30">
      <xsl:apply-templates select="*" mode="M30"/>
   </xsl:template>
   <xsl:param name="quotes"
              select="some $token in tokenize(/*/@conformance,'\s+')               satisfies lower-case($token)='quotes'"/>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="programlisting//text() | literal//text()"
                 priority="1001"
                 mode="M32">
      
   </xsl:template>

	  <!--RULE -->
   <xsl:template match="text()[$quotes]" priority="1000" mode="M32">

		<!--REPORT -->
      <xsl:if test="if( contains(.,'''') )                     then if( ancestor::*[@conformance][1]/@conformance='skip' )                          then false()                          else true()                     else false()">ASCII apostrophe character found in non-skipped text<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>

		    <!--REPORT -->
      <xsl:if test="if( matches(.,&#34;^[^&#34;&#34;]*&#34;&#34;[^&#34;&#34;]*$&#34;) )                     then if( ancestor::*[@conformance][1]/@conformance=&#34;skip&#34; )                          then false()                          else true()                     else false()">single ASCII quote character found in non-skipped text<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>

		    <!--REPORT -->
      <xsl:if test="if( matches(.,&#34;&#34;&#34;[^&#34;&#34;]*?&#34;&#34;&#34;) )                     then if( ancestor::*[@conformance][1]/@conformance=&#34;skip&#34; )                          then false()                          else true()                     else false()">pair of ASCII quote characters found in non-skipped text<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>

		    <!--REPORT -->
      <xsl:if test="if( matches(.,&#34;“”&#34;) )                     then if( ancestor::*[@conformance][1]/@conformance=&#34;skip&#34; )                          then false()                          else true()                     else false()">Empty pair of cursive quotes found in non-skipped text<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M32"/>
   <xsl:template match="@*|node()" priority="-2" mode="M32">
      <xsl:apply-templates select="*" mode="M32"/>
   </xsl:template>
   <xsl:param name="eBuzzwords"
              select="some $token in tokenize(/*/@conformance,'\s+')               satisfies lower-case($token)='ebuzzwords'"/>

   <!--PATTERN -->


	  <!--RULE -->
   <xsl:template match="text()[$eBuzzwords]" priority="1000" mode="M34">

		<!--REPORT -->
      <xsl:if test=".[matches(.,'\We[A-Z]')]                      [not(ancestor::*[@lang][1]/@lang='none')]">An eBuzzword has not been protected from the spell checker.<xsl:text xmlns:sch="http://www.ascc.net/xml/schematron">: </xsl:text>
         <xsl:apply-templates xmlns:sch="http://www.ascc.net/xml/schematron"
                              select="."
                              mode="schematron-get-full-path-3"/>
         <xsl:text xmlns:sch="http://www.ascc.net/xml/schematron" xml:space="preserve">
</xsl:text>
      </xsl:if>
      
   </xsl:template>
   <xsl:template match="text()" priority="-1" mode="M34"/>
   <xsl:template match="@*|node()" priority="-2" mode="M34">
      <xsl:apply-templates select="*" mode="M34"/>
   </xsl:template>
</xsl:stylesheet>
