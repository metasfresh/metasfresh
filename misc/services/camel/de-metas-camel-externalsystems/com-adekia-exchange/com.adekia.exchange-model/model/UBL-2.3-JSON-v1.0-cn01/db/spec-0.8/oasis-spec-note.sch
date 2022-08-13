<?xml version="1.0" encoding="UTF-8"?>
<!--
  This is the set of writing rules when creating an OASIS specification
  using the DocBook article document type.  This is expressed suitably
  for the oXygen XML to implement Schematron quick fixes.
  
  Issues of writing style and appearance are found in the 
  oasis-spec-note-style.sch constraints.

  $Id: oasis-spec-note.sch,v 1.22 2019/07/07 15:47:35 admin Exp $
-->
<schema xmlns="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="2.0"
  xmlns:sqf="http://www.schematron-quickfix.com/validator/process">
  
  <xsl:key name="xrefs" 
           match="*[@linkend,@linkends]" 
           use="@linkend,tokenize(@linkends,'\s+')"/>
  
  <!--no hanging paragraphs-->
  <pattern>
    <rule context="section[section]|appendix[section]">
      <report test="* except (section,title) and not(@conformance='skip')" 
        >Hanging content is not allowed</report>
<!--      <sqf:fix id="wrapIniitial">
        <sqf:description>
          <sqf:title>Wrap initial content into its own section</sqf:title>
        </sqf:description>
        <sqf:replace node-type="element" target="{name(.)}"
        xmlns:sqfu = "http://www.schematron-quickfix.com/utility">
          <sqfu:copy-of select="@*,title" unparsed-mode="true"/>
          <section id="S-XXXXX" xmlns="">
            <title>XXXXX</title>
            <sqfu:copy-of select="* except ( section, title )"
                          unparsed-mode="true"/>
          </section>
          <sqfu:copy-of select="section" unparsed-mode="true"/>
        </sqf:replace>
      </sqf:fix>-->
    </rule>
  </pattern>
  
  <!--any leading white-space?-->
  <pattern>
    <rule context="para">
      <report test="matches(node()[1]/self::text(),'^\s') and
                    normalize-space(node()[1]/self::text())"
        >Leading space in a paragraph</report>
      <report test="not(normalize-space(.)) and not(*) and
                    not(@conformance='skip')" sqf:fix="skipConformance"
        >An empty paragraph is indicative of an editing oversight</report>
      <sqf:fix id="skipConformance">
        <sqf:description>
          <sqf:title>Indicate to skip conformance</sqf:title>
        </sqf:description>
        <sqf:add node-type="attribute" target="conformance" select="'skip'"/>
      </sqf:fix>
    </rule>
  </pattern>

  <!--check links to oasis-open.org for being https as of November 2018-->
  <pattern>
    <rule context="ulink[starts-with(@url,'http:') and
                         contains(@url,'oasis-open.org')]
                        [not(@conformance='skip')]">
      <assert test="starts-with(@url,'https:')" sqf:fix="skipConformance"
        >References to OASIS web sites must use https where possible</assert>
      <sqf:fix id="skipConformance">
        <sqf:description>
          <sqf:title>Indicate to skip conformance</sqf:title>
        </sqf:description>
        <sqf:add node-type="attribute" target="conformance" select="'skip'"/>
      </sqf:fix>
    </rule>
  </pattern>

  <!--mismatched links that have not declared themselves to skip checking?-->
  <pattern>
    <rule context="ulink[normalize-space(.)][not(@conformance='skip')]">
      <report test="not(@url=.)" sqf:fix="skipConformance replaceURL"
        >The URI must match the text content.</report>
      <sqf:fix id="skipConformance">
        <sqf:description>
          <sqf:title>Indicate to skip conformance</sqf:title>
        </sqf:description>
        <sqf:add node-type="attribute" target="conformance" select="'skip'"/>
      </sqf:fix>
      <sqf:fix id="replaceURL">
        <sqf:description>
          <sqf:title>Replace URL with the value of the element</sqf:title>
        </sqf:description>
        <sqf:add node-type="attribute" target="url" select="."/>
      </sqf:fix>
    </rule>
  </pattern>

  <!--check for any BOM characters-->
  <pattern>
    <rule context="text()">
      <report test="contains(.,'﻿')" sqf:fix="removeBOM"
        >BOM character found in text</report>
      <sqf:fix id="removeBOM">
        <sqf:description>
          <sqf:title>Remove the BOM character from the text</sqf:title>
        </sqf:description>
        <sqf:replace match="." select="translate(.,'﻿','')"/>
      </sqf:fix>
    </rule>
    <rule context="@*">
      <report test="contains(.,'﻿')" sqf:fix="removeBOM"
      >BOM character found in attribute "<value-of select="name(.)"/>"</report>
      <sqf:fix id="removeBOM">
        <sqf:description>
          <sqf:title>Remove the BOM character from the attribute</sqf:title>
        </sqf:description>
        <sqf:replace node-type="attribute" target="{name(.)}" 
                     select="translate(.,'﻿','')"/>
      </sqf:fix>
    </rule>
  </pattern>
  
  <!--avoid hanging bibliographic references-->
  <pattern>
    <rule context="bibliomixed[not(@conformance='skip')]">
      <report test="not(@id)"
  >Every bibliographic entry must be referenceable using an identifier</report>
      <report test="not(key('xrefs',@id))"
  >Every bibliographic entry must be referenced (or skipped)</report>
    </rule>
  </pattern>
  
  <!--content needed for mocking up cover page of ISO publication-->
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='doc-sdo'])"
        >releaseinfo[@role='doc-sdo'] required for doc-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='doc-proj-id'])"
        >releaseinfo[@role='doc-proj-id'] required for doc-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='doc-language'])"
        >releaseinfo[@role='doc-language'] required for doc-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='doc-release-version'])"
        >releaseinfo[@role='doc-release-version'] required for doc-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-originator'])"
        >releaseinfo[@role='std-originator'] required for std-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-doc-type'])"
        >releaseinfo[@role='std-doc-type'] required for std-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-doc-number'])"
        >releaseinfo[@role='std-doc-number'] required for std-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-edition'])"
        >releaseinfo[@role='std-edition'] required for std-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-version'])"
        >releaseinfo[@role='std-version'] required for std-ident element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-ref-dated'])"
        >releaseinfo[@role='std-ref-dated'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='std-ref-undated'])"
        >releaseinfo[@role='std-ref-undated'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='doc-ref'])"
        >releaseinfo[@role='doc-ref'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='release-date'])"
        >releaseinfo[@role='release-date'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='comm-ref'])"
        >releaseinfo[@role='comm-ref'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='secretariat'])"
        >releaseinfo[@role='secretariat'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[releaseinfo[@role=
      ('doc-sdo','doc-proj-id','doc-language','doc-release-version',
       'std-originator','std-doc-type','std-doc-number','std-edition',
       'std-version','std-ref-dated','std-ref-undated',
       'doc-ref','release-date','comm-ref','secretariat','page-count')]]">
      <assert test="exists(releaseinfo[@role='page-count'])"
        >releaseinfo[@role='page-count'] required for iso-meta element</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="articleinfo[/article/articleinfo/releaseinfo/@role='doc-sdo']/copyright">
      <assert test="exists(year)"
        >The copyright year is required by ISO.</assert>
    </rule>    
    <rule context="articleinfo[/article/articleinfo/releaseinfo/@role='doc-sdo']/copyright">
      <assert test="exists(holder[not(@role)])"
        >The copyright holder (no @role attribute) is required by ISO.</assert>
    </rule>    
  </pattern>

  <pattern>
    <rule context=
      "variablelist[/article/articleinfo/releaseinfo/@role='doc-sdo']/varlistentry">
      <assert test="exists(term[not(@role) or
                     @role=('preferredTerm','admittedTerm','deprecatedTerm')])"
        >Every varlistentry must define a term with one or more groups of terms starting with either a default (no @role) or @role as one of 'preferredTerm', 'admittedTerm' or 'deprecatedTerm'.</assert>
      <assert test="every $term in term[not(@role) or
                     @role=('preferredTerm','admittedTerm','deprecatedTerm')]
                    satisfies 
                    count( $term/following-sibling::term[@role='partOfSpeech']
                                            [.=('noun','verb','adj','adv')] ) =
                    1 + count( $term/following-sibling::term[not(@role) or
                     @role=('preferredTerm','admittedTerm','deprecatedTerm')])"
        >Every varlistentry group of terms defined by default (no @role) or @role as one of 'preferredTerm', 'admittedTerm' or 'deprecatedTerm' must also have @role='partOfSpeech' with a value of: "noun" or "verb" or "adj" or "adv".</assert>
    </rule>
  </pattern>
  <pattern>
    <rule context="ulink[/article/articleinfo/releaseinfo/@role='doc-sdo']">
      <report test="exists(.//literal)"
        >DocBook2ISOSTS translation prohibits a ulink element to have a literal element as a descendant</report>
    </rule>
  </pattern>
  <pattern>
    <rule context="ulink[/article/articleinfo/releaseinfo/@role='doc-sdo']">
      <report test="exists(emphasis)"
        >DocBook2ISOSTS translation prohibits a ulink element to have an emphasis element as a child</report>
    </rule>
  </pattern>
  
  <pattern>
    <rule context="para">
      <assert test="empty(.//itemizedlist | .//orderedlist)"
        >A list is not allowed to be inside of a paragraph.</assert>
      <assert test="empty(.//example)"
        >An example is not allowed to be inside of a paragraph.</assert>
    </rule>
  </pattern>
  
  <pattern>
    <rule context="para">
      <assert test="empty(.//blockquote)"
        >A blockquote is not allowed to be inside of a paragraph.</assert>
    </rule>
  </pattern>
  
<!--
  The following writing styles are engaged by using a series of conformance
  tokens in the document element
-->
  
  <let name="ids" 
       value="some $token in tokenize(/*/@conformance,'\s+')
              satisfies lower-case($token)='ids'"/>
  
  <!--deterministic identifiers are required for all referenced items-->
  <!--note that alternative titles must be wrapped with <phrase condition="">
      and that this check only checks the phrase with condition="oasis"-->
  <pattern>
    <rule abstract="true" id="sectionIdentifier">
      <assert test="@id" sqf:fix="fixIdentifier"
        >The id= identifier is required.</assert>
      <report test="@id != concat(upper-case(substring(name(.),1,1)),'-',
                replace(translate(
      upper-case(normalize-space((title/phrase[@condition='oasis'],title)[1])),
                          ' /=','--'),'[^-A-Z\d.]',''))"
              sqf:fix="fixIdentifier"
        >The id= identifier must match the title.</report>
      <sqf:fix id="fixIdentifier">
        <let name="id" value="@id"/>
        <let name="refs" value="count(//@linkend[.=$id])"/>
        <sqf:description>
          <sqf:title>Change the identifier to match the title
          (refs: <xsl:value-of select="$refs"/>)</sqf:title>
        </sqf:description>
        <sqf:add node-type="attribute" target="id"
                 select="concat(upper-case(substring(name(.),1,1)),'-',
              replace(translate(upper-case(normalize-space(title)),' /=','--'),
                      '[^-A-Z\d.]',''))"/>
      </sqf:fix>
    </rule>
    <rule context="section[not(@conformance='skip')][$ids]|
                   appendix[not(@conformance='skip')][$ids]|
                   table[not(@conformance='skip')][$ids]|
                   figure[not(@conformance='skip')][$ids]">
      <extends rule="sectionIdentifier"/>
    </rule>
  </pattern>
  
  <let name="quotes" 
       value="some $token in tokenize(/*/@conformance,'\s+')
              satisfies lower-case($token)='quotes'"/>
  
  <!--check for dumb quotes and apostrophe; for performance reaseons the
      rule checking is expressed imperatively-->
  <pattern>
    <rule context="programlisting//text() | literal//text()">
      <!--ignore this rule in these contexts-->
    </rule>
    <rule context="text()[$quotes]">
      <report test="if( contains(.,'''') )
                    then if( ancestor::*[@conformance][1]/@conformance='skip' )
                         then false()
                         else true()
                    else false()" sqf:fix="replace5Q"
        >ASCII apostrophe character found in non-skipped text</report>
      <report test='if( matches(.,"^[^""]*""[^""]*$") )
                    then if( ancestor::*[@conformance][1]/@conformance="skip" )
                         then false()
                         else true()
                    else false()' sqf:fix="replaceDQ replaceDQL replaceDQR"
        >single ASCII quote character found in non-skipped text</report>
      <report test='if( matches(.,"""[^""]*?""") )
                    then if( ancestor::*[@conformance][1]/@conformance="skip" )
                         then false()
                         else true()
                    else false()' sqf:fix="replaceDQ replaceRegexDQLR replaceRegexDQRL"
        >pair of ASCII quote characters found in non-skipped text</report>
      <report test='if( matches(.,"&#8220;&#8221;") )
                    then if( ancestor::*[@conformance][1]/@conformance="skip" )
                         then false()
                         else true()
                    else false()'
        >Empty pair of cursive quotes found in non-skipped text</report>
      <sqf:fix id="replace5Q">
        <sqf:description>
          <sqf:title>Replace ASCII apostrophe with cursive &amp;#8217; (and all entities)</sqf:title>
        </sqf:description>
        <sqf:replace match="." select="replace(.,'''','&#8217;')"/>
      </sqf:fix>
      <sqf:fix id="replaceDQ">
        <sqf:description>
          <sqf:title>Replace ASCII quote with a pair of cursive quotes, one to be deleted &amp;#8220;&amp;#8221; (and all entities)</sqf:title>
        </sqf:description>
        <sqf:replace match="." select='replace(.,"""","&#8220;&#8221;")'/>
      </sqf:fix>
      <sqf:fix id="replaceDQL">
        <sqf:description>
          <sqf:title>Replace ASCII quote with a left cursive quote (and all entities)</sqf:title>
        </sqf:description>
        <sqf:replace match="." select='replace(.,"""","&#8220;")'/>
      </sqf:fix>
      <sqf:fix id="replaceDQR">
        <sqf:description>
          <sqf:title>Replace ASCII quote with a right cursive quote (and all entities)</sqf:title>
        </sqf:description>
        <sqf:replace match="." select='replace(.,"""","&#8221;")'/>
      </sqf:fix>
      <sqf:fix id="replaceRegexDQLR">
        <sqf:description>
          <sqf:title>Replace double-quoted string with &amp;#8220;&amp;#8221; (and all entities); Warning: this will not work across node boundaries.</sqf:title>
        </sqf:description>
        <sqf:replace match="." select='replace(.,"""([^""]*?)""","&#8220;$1&#8221;")'/>
      </sqf:fix>
      <sqf:fix id="replaceRegexDQRL">
        <sqf:description>
          <sqf:title>Replace double-quoted string with inverted &amp;#8221;&amp;#8220; (and all entities); Warning: this will not work across node boundaries.</sqf:title>
        </sqf:description>
        <sqf:replace match="." select='replace(.,"""([^""]*?)""","&#8221;$1&#8220;")'/>
      </sqf:fix>
    </rule>
  </pattern>

  <let name="eBuzzwords" 
       value="some $token in tokenize(/*/@conformance,'\s+')
              satisfies lower-case($token)='ebuzzwords'"/>
  
  <!--avoid spelling of eBuzzword-->
  <pattern>
    <rule context="text()[$eBuzzwords]">
      <report test=".[matches(.,'\We[A-Z]')]
                     [not(ancestor::*[@lang][1]/@lang='none')]"
                sqf:fix="unspellBuzzword"
        >An eBuzzword has not been protected from the spell checker.</report>
      <sqf:fix id="unspellBuzzword">
        <sqf:description>
          <sqf:title>Avoid the spell checker from checking eBuzzwords</sqf:title>
        </sqf:description>
        <sqf:stringReplace regex="(\W)(e[A-Z]\w+)">
          <xsl:analyze-string regex="(\W)(e[A-Z]\w+)" select=".">
            <xsl:matching-substring>
              <xsl:value-of select="regex-group(1)"/>
              <phrase xmlns="" lang="none">
                <xsl:value-of select="regex-group(2)"/>
              </phrase>
            </xsl:matching-substring>
          </xsl:analyze-string>
        </sqf:stringReplace>
      </sqf:fix>
    </rule>
  </pattern>

</schema>