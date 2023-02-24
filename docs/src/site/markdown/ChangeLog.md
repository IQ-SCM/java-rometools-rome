::: section
## Change Log

::: section
### Changes made since v1.0

1.  [Issue 127](http://java.net/jira/browse/ROME-127){.externalLink}:
    Rome 1.0 not JDK 1.4 compatible
:::

::: section
### Changes made since v1.0RC2

1.  [Issue 121](http://java.net/jira/browse/ROME-121){.externalLink}:
    RSS item category iteration should try to reflect document order
2.  New property preserveWireFeed available on SyndFeedInput\
    WireFeeds will be preserved if the property preserveWireFeed is set
    on the SyndFeedInput object it is built from. Atom/RSS Entry/Item
    objects are also available from SyndEntry objects if the WireFeed is
    preserved using the new getWireEntry() method. See [Preserving
    WireFeeds (rome)](./PreservingWireFeeds.html) for details.
:::

::: section
### Changes made since v1.0RC1

1.  Fix. Date parsing for Atom10 entry and additional W3C masks\
    Item date elements were being parsed with the W3C parser instead the
    lenient one (RFC822 + W3C + custom masks).\
    The following masks were added to W3C masks to handle RFC822
    timezone (ie \'-800\'):

    ```
        yyyy-MM-dd'T'HH:mm:ssZ yyyy-MM-dd't'HH:mm:sszZ
    ```
2.  Fix. Contributors properties in SyndEntry were not implementing the
    semantics of list properties.\
    They were returning NULL instead, now they return an empty list if
    not values are set.
3.  Fix. Contributors properties in SyndEntry and SyndFeed were not
    being converted to/from WireFeed
4.  Fix. Syndication Module Generator was failing if some of its values
    were null.\
    Checks for nulll have been added it to the generator to prevent
    NullPointerExceptions
5.  New. Added new constructor to XmlReader

    ```java
        public XmlReader(InputStream is, boolean lenient, String defaultEncoding)
    ```
6.  New. Support atom person construct extensions, using the Extendable
    interface on SyndPerson:\
    Patch from James Roper. See [Issue
    1101](http://java.net/jira/browse/ROME-110){.externalLink} for
    details
7.  New. Maven 2 build for main project\
    ROME can now be built with Maven 2
8.  New. OSGi support\
    OSGi headers to MANIFEST.MF so that rome.jar can also be used in an
    OSGi environment. See [Issue
    117](http://java.net/jira/browse/ROME-117){.externalLink} for
    details.
9.  New. Allow pretty printing to be turned on and off\
    see [Issue 114](http://java.net/jira/browse/ROME-114){.externalLink}
    for details. Thanks to Martin Kurz for the patch.
10. Configurable classloading behavior for OSGi compatibility.\
    We have received a report of some issues with plugin loading in an
    OSGi environment ([Issue
    118](http://java.net/jira/browse/ROME-118){.externalLink}). The fix
    appears to be to change Class.forName to classLoader.loadClass, but
    the semantics for this are subtly different, so we have made this
    new behavior user selectable. Set the
    \"rome.pluginmanager.useloadclass\" system property to \"true\" to
    enable it.
11. More lenient number parsing\
    There were a number of problems with feeds providing blank or
    invalid values in fields which would be numbers. ROME will now
    handles these better. See issues
    [104](http://java.net/jira/browse/ROME-104){.externalLink},
    [107](http://java.net/jira/browse/ROME-107){.externalLink} and
    [108](http://java.net/jira/browse/ROME-108){.externalLink} for
    details.
:::

::: section
### Changes made from v0.9 to v1.0RC1

1.  New. XmlReader support for default encoding\
    The XmlReader can be set with an alternate default encoding in case
    no encoding has been detected from the transport (HTTP), the stream
    or the XML prolog. if no value is set the default fallback rules
    based on the content-type will be used. The alternate default
    encoding can be set/viewed via a static methods,
    **setDefaultEncoding()** and **getDefaultEncoding()**.
2.  Fix. Atom 1.0 links were generated without title and length
    attributes.\
    The Atom 1.0 Generator was not generating title and length
    attributes when values are present.
3.  Fix. XmlReader, multi-line prolog encoding detection.\
    XmlReader handles properly xml-prolog detection when prolog goes
    over multiple lies (such as G groups feeds).
4.  Fix. Base64 decoding was failing under certain padding conditions.
5.  Fix. XmlReader fixes\
    Fixed bug that if BOM is UTF8 was not being set to UTF8. Changed
    logic to use Buffered stream instead pushback stream for all
    encoding detection. Changed logic of xml prolog detection to avoid
    having a buffer with half of a unicode character (instead filling up
    the buffer looking up to first \'\>\' which means it a valid
    buffer).
6.  New. XmlReader supports default encoding at instance level.\
    Via a new constructor is possible to indicate a default encoding
    different than the default encoding at class level.
7.  Fix. Making the EqualsBean to follow equals contract.\
    For X.equals(null) it was throwing a NullPointerException, now it
    returns FALSE.
8.  Fix. Render Atom icon and logo attributes.\
    AtomGenerator now adds icon and logo elements to xml tree
9.  Fix. Updated AtomPub namespace to its permenent home.\
    AtomService namespace updated to
    [http://www.w3.org/2007/app](http://www.w3.org/2007/app){.externalLink}
10. New. Added support for configuration per classloader level.\
    The PluginManager (handles Parsers and Generators) now is singleton
    at classloader level allowing different configurations in different
    classloaders.
11. Atom parser: better relative URI handling\
    Instead of simply resolving each relative URI at runtime and saving
    only the resolved one, we now save both the relative URI and the
    resolve one. We introduced the following new methods to provide
    access to the resolved URI.
    -   Link.getLinkResolved()
    -   Link.setLinkResolved()
    -   Category.getSchemeResolved()
    -   Category.setSchemeResolved()
    -   Person.getUriResolved()
    -   Person.setUriResolved()
12. Utility methods useful in working with Atom protocol feeds\
    Added a couple of methods to make it easier to deal with Atompub
    feeds.
    -   Entry.isMediaEntry()
    -   Atom10Parser.parseEntry()
    -   Atom10Generator.serializeEntry()
13. Bugs fixed\
    Fixed the following bugs:
    -   49 Better content/summary mapping
    -   53 Content.setType not working with subtitles atom 1.0
    -   56 fix of bug #39 leads to invalid atom feeds
    -   63 Missing link attribute when generating Atom 1.0
    -   64 ROME\'s Atom parser doesn\'t pick up multiple alt links
    -   65 Atom feeds not including logo image
    -   71 encoding problem in XmlReader.getXmlProlog()
    -   79 Feed.setIcon()/setLogo() ignored by Atom10Generator
    -   81 SyndFeedImpl.equals() does not obey equals contract
14. Fix. Parsers where ignoring namespaced prefixed Attributes.\
    If an XML feed uses a prefix for the Atom elements and the
    attributes of Atom elements use the prefix the parser was not
    picking up those attributes.\
    The fix makes the parser to look for prefixed and non-prefixed
    attributes.
15. Fix. Atom Feed and Entry beans author and category property getters\
    They were returning NULL when there were not authors or categories,
    they must return an empty list.
16. New. Switch to enable/disable relative URI resolution in Atom 1.0
    Parser.\
    The Atom10Parser class has a static method, setResolveURIs(boolean)
    that enables/disables relative URI resolution.
17. New. XmlReader handling content-type charset values has been
    relaxed.\
    XmlReader handles content-type charset encoding value within single
    quotes and double quotes.
18. Fix. Links, authors and contributors properties in SyndFeed were not
    implementing the semantics of list properties.\
    They were returning NULL instead, now they return an empty list if
    not values are set.
19. Fix. RSS conversion of a SyndFeed was losing the link of the feed if
    the links property was used instead the link property.\
    Over time the SyndFeed has been modified to support more Atom
    specific properties and their cardinality, conversion to RSS of
    these properties was not always taken care.\
    The RSS converter has been changed so the link from SyndFeed is
    taken as channel link and if not set the first value of the links
    property is taken.
20. Fix. WireFeedInput throws IllegalArgumentException if the feed type
    is not recognized.\
    Previously the IllegalArgumentException was wrapped by a
    ParsingFeedException (Reported by [Issue
    91](http://java.net/jira/browse/ROME-91){.externalLink}).
21. Fix. SyndFeedImpl.equals(other) checks for instance of other before
    casting.\
    The underlying ObjectBean does this check, but in this method a cast
    is done before to obtain the foreign markup, no the instance check
    is peformed before to avoid a class cast exception.
22. Fix. Atom content based elements related fixes
    -   Atom 0.3 Parser/Generator
        -   Changed title to be treated as a Content construct.
            ([http://www.mnot.net/drafts/draft-nottingham-atom-format-02.html#rfc.section.4.3](http://www.mnot.net/drafts/draft-nottingham-atom-format-02.html#rfc.section.4.3){.externalLink})
    -   Atom 1.0 Generator:
        -   changed feed title/subtitle and entry title to be treated as
            Content constructs. (Parser had this implemented already.)
        -   added title attribute to links. (Parser had this implemented
            already.)
        -   fixed content parsing for some XML content types. e.g.
            (application/xhtml+xml)
23. Fix. Atom link and enclosures handling
    -   Atom 0.3 Converter
        -   fixed link parsing code to parse all links (not just the
            first alternate link) and added enclosure support via link
            rel=\"enclosure\".
        -   changed title conversion to use Content instead of plain
            text.
    -   Atom 1.0 Converter
        -   added SyndEnclosure to atom:link rel=enclosure conversion.
24. Fix. RSS 1.0 URI generation
    -   RSS 1.0 Generator
        -   channel/items/Seq/li/@resource now get\'s the item URI
            instead of the Link.
            ([http://web.resource.org/rss/1.0/spec#s5.3.5](http://web.resource.org/rss/1.0/spec#s5.3.5){.externalLink})
25. Fix. Javadocs corrections.
    -   Fixed some javadoc comments for SyndEntry.
26. Fix. Atom content based elements were not parsed with XML mime
    types.\
    If the mime type was and XML mime type the content value was being
    lost on parsing.
27. Fix. duplication of content:encoded elements when reading/writing
    and RSS feed.\
    content:encoded elements are treated special, without a module, they
    have to be removed from the foreign markup to avoid duplication in
    case of read/write. Note that this fix will break if a content
    module is used.
28. New. XmlFixerReader converts \'&\' into \'&\' when there is no
    matching entity.\
    Feeds commonly use \'&\' instead \'&\' in their content, this change
    converts those orphant \'&\'s into \'&\'s.
29. Fix. RSS090Parser does not set the URI property.\
    The fix honors the documentation \"For RSS 0.91, RSS 0.92, RSS 0.93
    & RSS 1.0 \... the SyndEntry uri property will be set with the value
    of the link element\...\"
30. New. Removal of all unused namespaces from generated feeds.\
    The generators now remove all unused namespaces from the XML
    document before generating it.
:::

::: section
### Changes made from v0.8 to v0.9

1.  Design changes
    -   Support Atom feed.title, feed.subtitle and entry.title [Issue
        48](http://java.net/jira/browse/ROME-48){.externalLink}\
        #48 fixed via better support for Atom text constructs title and
        subtitle. Added get/setTitleEx() and get/setSubtitleEx(), which
        get get SyndContent objects. Title and subtitle still available
        from old getters/setters.
    -   Support for mapping Atom summary/content to RSS
        description/content
        [https://rome.dev.java.net/servlets/ReadMsg?list=dev&msgNo=1680](https://rome.dev.java.net/servlets/ReadMsg?list=dev&msgNo=1680){.externalLink}
    -   Fixed by introduced Content object in RSS model. ROME now parses
        as RSS Content. That makes parsing easier and allows us to
        support a more logical summary/content mapping:
        -   RSS to/from Atom
        -   RSS to/from Atom
2.  General parsing fixes
    -   XmlReader xml prolog regular expression does not allow for
        single quotes [Issue
        36](http://java.net/jira/browse/ROME-36){.externalLink}\
        The XmlReader was only parsing prolog encodings within double
        quotes, the regular expression to detect the encoding has been
        change to detect single or double quotes.
    -   Fix. XML prolog parsing now support whitespaces around \'=\'\
        If the XML prolog contained spaces around the \'=\' between the
        encoding attribute name and the encoding attribute value the
        encoding was not being detected. The fix accepts all valid
        whitespace characters (as defined in the XML spec).
    -   RSS parser does not recognize version=\"2.00\" [Issue
        33](http://java.net/jira/browse/ROME-33){.externalLink}
    -   Atom 1.0 Text Types Not Set Correctly [Issue
        39](http://java.net/jira/browse/ROME-39){.externalLink}
    -   Security issue [Issue
        46](http://java.net/jira/browse/ROME-46){.externalLink}
    -   Fix for the potential problem outlined in
        [http://www.securiteam.com/securitynews/6D0100A5PU.html](http://www.securiteam.com/securitynews/6D0100A5PU.html){.externalLink}.
        Thanks to Nelson Minar for bringing this to our attention.
    -   Fix. Wrong default description type for RSS 2.0 Fix for [Issue
        26](http://java.net/jira/browse/ROME-26){.externalLink}
    -   Change default description type for RSS 2.0 from text/plain to
        text/html as per RSS 2.0 spec
    -   Fix to add all HTML4 entities, according to
        [http://www.w3.org/TR/REC-html40/sgml/entities.html](http://www.w3.org/TR/REC-html40/sgml/entities.html){.externalLink}
        specially for the HTMLsymbol set (Mathematical, Greek and
        Symbolic characters for HTML) and the HTMLspecial set (Special
        characters for HTML).
3.  Date parsing fixes
    -   Additional version and date leniency could extract more
        information [Issue
        24](http://java.net/jira/browse/ROME-24){.externalLink}
    -   Non RFC822 Dates not processed in RSS pubDate field [Issue
        27](http://java.net/jira/browse/ROME-27){.externalLink}
        -   RSS feed parsers were were only parsing RFC822 dates because
            they were not using the proper date-time parsing function
            for the date-time elements.
        -   If a W3C date-time element had no time component it was
            being parsed as local time instead of GMT, ROME DateParser
            class has been modified to use GMT in this situation.
        -   Current JDKs do not handle \'UT\' timezone indicator, ROME
            DateParser class has been modified to handle it.
        -   Use Atom updated instead of published [Issue
            41](http://java.net/jira/browse/ROME-41){.externalLink}
        -   Atom 1.0 Date (Updated or Published) Not Set [Issue
            42](http://java.net/jira/browse/ROME-42){.externalLink}
        -   lastBuildDate does not populate publishedDate [Issue
            43](http://java.net/jira/browse/ROME-43){.externalLink}
            Provides a feed date for RSS 0.91 feeds that specify
            lastBuildDate but not pubDate.
    -   Fix. Parsing some numeric elements was failing due to
        whitespaces The image.width and image.height of RSS091U, the
        frequency of SyModule and the cloud.port of RSS092 elements are
        now being trimmed before doing the integer parsing.
4.  Atom link and URI fixes
    -   Improper relative link resolution in Atom10Parser [Issue
        37](http://java.net/jira/browse/ROME-37){.externalLink}
    -   ATOM 1.0 Entry links parsing [Issue
        38](http://java.net/jira/browse/ROME-38){.externalLink}
    -   ConverterForRSS10.java does not set URI for item [Issue
        25](http://java.net/jira/browse/ROME-25){.externalLink}
    -   Valid IRI href attributes are stripped for atom:link [Issue
        34](http://java.net/jira/browse/ROME-34){.externalLink}
5.  Module fixes
    -   iTunes Module has incorrect author and category support [Issue
        35](http://java.net/jira/browse/ROME-35){.externalLink}
    -   mediarss.io.MediaModuleParser NumberFormatException [Issue
        45](http://java.net/jira/browse/ROME-45){.externalLink}
    -   Slash module not serializable for FeedFetcher [Issue
        44](http://java.net/jira/browse/ROME-44){.externalLink}
:::

::: section
### Changes made from v0.7 to v0.8

1.  Change. Added enclosure support at Synd\* level\
    A new bean for handling enclosures at Synd\* level has been created
    (SyndEnclosure/SyndEnclosureImpl, interface/implementation).\
    The SyndEntry/SyndEntryImpl bean has a new \'enclosures\' property
    which returns the list of enclosures for that item.\
    The Wire\* to Synd\* converters for RSS propagate enclosures in both
    directions.\
    This enables handling enclosures from RSS 0.92, 0.93, 0.94 and 2.0
    at Synd\* level\
    Test cases have been modified to cover enclosures at Synd\* level.
2.  Change/Fix. Synd\* - Atom entry dates mapping
    -   (Change) Atom entries have 3 dates, \'modified\', \'issued\' and
        \'created\'. Synd entries have only 1 date property
        \'publishedDate\'. When converting from Atom to Synd the first
        not null date in the order above will be the one set in the Synd
        entry bean.
    -   (Fix) When converting from Synd to Atom the Synd entries
        \'publishedDate\' property value is set in both \'modified\' and
        \'issued\' properties of the Atom entry.\
        This Change/Fix is to be aligned with the Atom 0.3 spec.
3.  Fix. Trim enclosure length attribute\
    Fix from Trey Drake: At least 1 podcast site (ESPN) occasionally
    leaves trailing spaces in the enclosure content length attribute.
    This causes a NumberFormatException.
4.  Fix. Conversion to RSS 1.0 if Channel URI is not specified\
    Fix for problem converting to RSS 1.0 if not URI is specified at the
    channel level (it will now attempt to use the Link element)
5.  Changes to support Atom 1.0
    -   In com.sun.syndication.synd, added SyndLink and SyndPerson.
    -   In SyndEntry added. In SyndEntry, added summary, updatedDate,
        links collection and support for multiple authors.
    -   In com.sun.syndication.synd.impl, added Atom10Parser.java,
        Atom10Generator.java and ConverterForAtom10.java.
:::

::: section
### Changes made from v0.6 to v0.7

1.  Fix. RFC-882 dates parsing and generation were using localized names
    for day and month names\
    The date parser and generator were using the JVM default Locale
    instead forcing an English Locale to use day and month names in
    English as specified by RFC-822. Now US Locale is used.
2.  Fix. The \'ttl\' element of RSS0.94 and RSS2.0 feeds was not being
    parsed\
    The parsers now parse the \'ttl\' element and it is available in the
    resulting Channel bean. Note that \'ttl\' info is not available in
    the SyndFeed bean, thus it\'s lost when converting from WireFeed to
    SyndFeed.
3.  Change. RSS enclosures with empty \'length\' attributes are
    accepted\
    Parsing an RSS feed with an enclosure where the length attribute was
    an empty String were failing. Now they are parsed and the length is
    set to 0.
4.  Change. RSS 1.0 feeds use URI/Link for unique ID (rdf:about).\
    RSS 1.0 specification recommends that the rdf:about attribute URI
    use the value of the item\'s link element, though this could be
    different if the user chooses to override it by specifying their own
    URI. RSS 1.0 feeds now use the URI if specified, otherwise the link
    for the item.
5.  Fix. toString() was reporting NullPointerException with List
    properties\
    When a List (or Map) property had a NULL element the toString()
    logic was failing partially due to a NullPointerException.
6.  Fix. DC creator elements were being lost when converting to
    SyndFeed\
    DC creator elements were being lost when converting to SyndFeed.
    This was happening with RSS versions that have native author
    elements (0.94 and 2.0) and for the managingEditor element at
    channel level (available in 0.91 Userland and onwards).
7.  Change. Date and enumeration elements are trimmed during parsing\
    There are some feeds that add whitespaces or carriage return
    characters before or after the proper date or enumeration value.
    This was causing ROME to fail processing those elements. This is
    taken care now as all dates elements in all feed types and Modules
    and the \'channel.skipHours.hour\' and \'channel.skipDays.day\'
    (RSS0.91 - RSS2.0) are trimmed before parsing and setting their
    values in the beans.
8.  Fix. SyndFeed description now maps to atom:tagline\
    Previously, atom:info was being mapped to the feed\'s description.
    According to the Atom03 spec atom:info should be ignored by parsers,
    and the more appropriate element is atom:tagline.
9.  Fix. RSS cloud is now generated/parsed correctly\
    The \'path\' attribute from the cloud was not being
    generated/parsed. The parser now process all cloud attributes and
    set the cloud to the channel.
10. Fix. RFC-822 2 digit years\
    Previously RFC-822 dates did not work correctly with 2 digit years.
    This is now fixed.
11. Fix. No alternate link causes IndexOutOfBoundsException\
    Fix bug where no alternate link causes IndexOutOfBoundsException in
    ConverterForAtom03 (Thanks to Joseph Van Valen).
12. Change. Date parsing attemps RFC822 on W3C parsing on all feeds\
    All feed parsers (RSS and Atom) now attemp both RFC822 and W3C
    parsing on date values.
13. Fix. XmlFixerReader removes character from stream when parsing an
    entity that contains an invalid character\
    Fix bug in XmlFixerReader where an invalid entity such as \"&ent=\",
    gets put back on the stream without the last character (in this
    example, \"&ent=\" becomes \"&ent\"). This was most visible when the
    XmlFixerReader encountered an URL with a query string that has more
    than one parameter (e.g.
    [http://www.url.com/index.html?qp1=1&qp2=2](http://www.url.com/index.html?qp1=1&qp2=2){.externalLink})
    \-- all \"=\" after the first one would disappear.
14. Change. DateParser can use additional custom datetime masks\
    Besides attempting to parse datetime values in W3C and RFC822
    formats additional datetime masks can be specified in the
    /rome.properties files using the \'datetime.extra.masks\' property.
    To indicate multiple masks the \'\|\' character must be used, all
    other characters are considered part of the mask. As with
    parser/generators/converter plugins the masks are read from all
    /rome.properties file in the classpath.
:::

::: section
### Changes made from v0.5 to v0.6

1.  Fix. W3C date-time parsing now handles date-time with \'Z\'
    modifier\
    The W3C date-time parser was not parsing times using the UTC
    modifier \'Z\'.
2.  Fix. XML prolog encoding parsing was failing when other attributes
    where present in the prolog\
    If there was an attribute following the encoding attribute the value
    of the encoding attribute was misinterpreted. For example, for the
    XML prolog the detected encoding was `UTF-8" standalone="yes`
    instead of `UTF-8`.
3.  Change. XmlReader lenient behavior gives priority to XML prolog
    encoding over content-type charset\
    In ROME 0.5 the XmlReader first attempts to do a strict charset
    encoding detection. Only if the strict detection fails it attempts a
    lenient detection. When the HTTP Content-Type header is of type
    `text/*xml` and the header does not specify any charset, RFC 3023
    mandates that the charset encoding must be `US-ASCII`. It\'s a
    common error for sites to use the `text/*xml` MIME type without
    charset information and indicate the charset encoding in the XML
    prolog of the document, being the charset encoding in the XML prolog
    different from `US-ASCII`. The XmlReader lenient behavior has been
    modified to give precedence to the XML prolog charset encoding, if
    present, over the HTTP Content-Type charset encoding.
4.  Addition. XML Healer\
    ROME parsers, SyndFeedInput and WireFeedInput, have a new feature,
    XML healing.\
    The XML healing trims the beginning of the XML text document if
    there are whitespaces, enters or XML comments before the XML prolog
    or the root element. It also replaces all HTML literal entities
    occurrences with coded entities. These changes convert feeds
    technically invalid (from the XML specification perspective) into
    valid ones allowing the SAX XML parser to successfully parse the XML
    if there are not other errors in it.\
    This behavior is active by default. It can be turned on and off
    using the new \'xmlHealerOn\' property in the SyndFeedInput and
    WireFeedInput classes.\
    The idea for this feature was taken from the FeedFilter from
    Jakarta\'s commons feedparser.
5.  Addition. The XML prolog of generated feeds contains the feed
    encoding\
    ROME generators were creating feeds with the XML prolog encoding
    always set to \'UTF-8\', if the given Writer had another charset
    encoding things would break for anybody consuming the feed (a
    mismatch between the char stream charset and what the XML doc
    says).\
    The SyndFeedOutput and WireFeedOutput generators now use the
    SyndFeed and WireFeed \'encoding\' property to set the \'encoding\'
    attribute in the XML prolog of the generated feeds. It is the
    responsibility of the developer to ensure that if the String is
    written to a character stream the stream charset is the same as the
    feed encoding property.
6.  Change. SyndFeed to Atom convertion now uses \'escaped\' mode for
    content elements\
    SyndFeed to Atom converter was using \'xml\' mode for content
    elements. This was breaking feeds with content that was not
    propertly escaped as it was assumed to be XML fragments.
7.  Change. RSS 2.0 parser and generator now handles DC Module\
    ROME configuration has been changed so RSS 2.0 parser and generator
    handle DC Module elements at channel level and item level.
8.  Fix. RSS0.93, RSS0.94 and RSS2.0 \'dc:date\' element value was being
    lost under certain conditions\
    If a feed had \'dc:date\' elements but not \'pubDate\' elements, the
    \'dc:date\' elements where lost when converting from Channel to
    SyndFeed.\
    This was happening for \'dc:date\' elements at channel level and at
    item level.
9.  Fix. RSS 1.0 \'rdf:resource\' and \'rdf:about\' item linking
    attributes use a unique ID now\
    The value for the \'rdf:resource\' and \'rdf:about\' linking
    attributes was done using the value of the \'link\' element. If
    there is more than one item with the same link the generated feed
    will be incorrect.\
    Instead using the link value now the index of the item is used for
    the linkage between \'rdf:resource\' and \'rdf:about\' for items.
10. Fix/Change. Parsing and setting of enumerated elements is case
    insentive now\
    Parsing and bean setting of enumerated values (such as RSS skipDay,
    Atom content mode, etc) are now case insentive, generation is strict
    (Postel Law).
11. Fix. Remove enumeration check on \'rel\' attribute of Atom link
    elements\
    Because a misunderstanding of Atom 0.3 specification the Atom Link
    bean was checking the value of the \'rel\' property against a set of
    valid values. The check has been removed.
12. Fix. DC subjects (in RSS versions with native categories) were being
    lost on conversion to SyndFeed\
    All RSS versions with native categories (at both channel and item
    level) now have the following behavior when converting to SyndFeed.\
    DC subjects are converted to SyndCategories. Native categories are
    converted to SyndCategories. They are both aggregated in a Set (to
    remove duplicates) then added to the SyndFeed.\
    When doing a SyndFeed to Channel conversion, if the RSS version has
    native categories and handles DC modules the categories will be
    duplicated as native and DC ones.
13. Fix/Change. RSS 1.0 rdf:about attribute in the channel.\
    RSS 1.0 uses the rdf:about attribute at the channel level as an
    identifier. This was not being parsed or generated (only supported
    at the item level). Support for this was added along with test
    cases.
14. Fix/Change/Addition. Multivalued Dublin Core element support.\
    Many feeds are using multiple DC elements to tag metadata, the
    interface for the DCModule has been changed to support Lists of
    elements, compatibility has been maintained with the existing
    interface though as the new methods are plural (creators vs.
    creator), the single value methods will remain as convenience
    methods. The implementation now uses the lists to represent all of
    the elements. The parser/generator modules for DC have been updated
    to reflect these changes along with a few other code cleanups in the
    DC\* modules.
15. Fix. Removed length constraint checks from RSS1.0 generator\
    RSS1.0 specification does not require, only suggests, maximum length
    for some of the elements. ROME was enforcing those lenghts when
    generating RSS1.0 feeds. This enforcement has been removed becuase
    is not mandatory.
:::

::: section
### Changes made from v0.4 to v0.5

1.  Change. Got rid of Enum class\
    All constants in the beans are Strings now, the corresponding
    property setters check that the value being set is one of the valid
    constants. Rome has not business defining an Enum class.
2.  Change. Got rid of ToString interface\
    This is just an implementation convenience, it was polluting Rome
    API. Modified ToStringBean to work without requiring an interface
    and method to propagate the prefix to use with properties.
3.  Change. ObjectBean, ToStringBean, EqualsBean are not part of the
    public API anymore\
    These are just an implementation convenience, they were polluting
    Rome API. Rome bean implementations don\'t extends ObjectBean
    anymore. Instead they use it in a delegation pattern. While these
    classes are not public anymore they are part of Rome implementation.
4.  Change. CopyFrom interface moved to com.sun.syndication.feed
    package\
    The common package is gone now that the \*Bean classes are not there
    anymore. No point keeping a package just for an interface.
5.  Fix. PluginManager was not doing plugin lookup in the defined order\
    PluginManager (manages parsers, generators and convertors for feeds
    and modules) was not doing the lookup in order the plugins are
    defined in the rome.properties files. This is needed for parsers
    where the lookup involves detecting the feed type and the feed type
    detection goes needs to go from stronger to weaker.
6.  Addition. Rome now recognizes RSS 2.0 feeds with
    \'http://backend.userland.com/rss2\' namespace\
    These namespace was defined by an RSS 2.0 draft and later was
    dropped. There are feeds out there using this namespace and Rome was
    not parsing them.
7.  Change. By default XmlReader does a lenient charset encoding
    detection.\
    If the charset encoding cannot be determined per HTTP MIME and XML
    specifications the following relaxed detection is performed: If the
    content type is \'text/html\' it replaces it with \'text/xml\' and
    tries the per specifications detection again. Else if the XML prolog
    had a charset encoding that encoding is used. Else if the content
    type had a charset encoding that encoding is used. Else \'UTF-8\' is
    used.\
    There are 2 new constructors that take a lenient flag to allow
    strict charset encoding detection. If scrict charset encoding
    detection is performed and it fails an XmlReaderException is thrown
    by the constructor. The XmlReaderException contains all the charset
    encoding information gathered from the stream including the
    unconsumed stream.
:::

::: section
### Changes made from v0.3 to v0.4

1.  Fix. Date elements on generated feeds use the right format now\
    There were some Date elements in Atom 0.3, DCModule, SyModule, RSS
    0.91 and RSS 0.93 that were incorrectly formating the date \[they
    were just doing a toString() \]. Now they use the RFC822 and W3C
    format as indicated in their specs.
2.  Fix. SyndFeed and SyndEntry getModule(DCModule.URI) method always
    returns a DCModule, never null\
    This issue is related to Fix #19 in v0.3. The DCModule is
    \'special\' for Synd\*, it must always be there. If it is not, it is
    created implicitly when needed. This was not happening when asking
    explicitly for the DCModule through the Synd\* interfaces.
3.  Addition. Added ParseFeedException to the \*Input classes\
    This new exception report the line and column number in the XML
    document where the parsing has failed.
4.  Change. Renamed \'\*I\' interfaces to just \'\*\' and default
    implementations to \'\*Impl\'\
    The Synd\* and Module interfaces/classes were affected. For example
    the interface that used to be SyndFeedI is now SyndFeed and the
    class that used to be SyndFeed is now SyndFeedImpl.
5.  Change. Ant \'build.xml\' files have been improved\
    The build.xml were re-written instead just using the Maven generated
    ones.
6.  Fix. DateParser now uses lenient parsing so as to work with JDK 1.5\
    DateParser currently setLenient to false. This does not work with
    JDK 1.5. Changing the DateParser to setLient to true.
7.  Change. SyndCategoryListFacade is not a public class anymore\
    It\'s now a package private class (it should have been like that in
    the first place).
8.  Addition. Added a protected constructor to the Synd\*Impl classes\
    This constructor takes a Class parameter. It allows implementations
    extending SyndContentImpl to be able to use the ObjectBean
    functionality with extended interfaces (additional public
    properties). Use case: Hibernate beans that have an \'Id\' Long
    property, a new interface HSynd\* (extending Synd\*) and a new
    implementation HSynd\*Impl (extending Synd\*Impl) where the clone(),
    equals(), hashCode() and toString() methods take the properties of
    the extension into account.
9.  Project layout change. Moved samples project to subprojects dir\
    The \'samples\' project was moved from \'rome/modules/samples\' to
    \'rome/subprojects/samples\'. The \'rome/modules\' project is left
    for Module subprojects only.
10. Fix. Plugin manager bug didn\'t allow custom plugins to replace core
    plugins\
    All plugins are in a Map keyed off by its type (parsers, generators,
    converters) or URI (modules). There is also a helper List containing
    the plugins, this list is scanned when looking for a plugin (for
    example when selecting the right parser). Plugins were added to the
    List without checking if another element in the List was using the
    same key. Now the List is built using the Map values, thus the
    overwriting works fine.
11. Fix. RSS 2.0 Converter (Wire -\> Synd - Wire) was not processing
    modules\
    The RSS 2.0 Converter now processes feed and entry modules both
    ways.
12. Fix. New properties introspection mechanism in common classes\
    The java.beans.Introspector does not work as expected on interface
    properties (it doesn\'t scan properties of the super interfaces).
    Now, a private implementation of a properties introspector is used
    by the common bean classes.
13. Change. Refactored private CopyFrom helper class\
    It was com.sun.syndication.feed.synd.impl.SyndCopyFrom now it is
    com.sun.syndication.common.impl.CopyFromHelper.
14. Fix. RSS2.0 Wire-Synd converter handles propertly RSS and DC
    categories\
    If the RSS2.0 feed had DC Module categories (subjects) this would
    override the RSS native categories. Now they are aggregated.
15. Change/Fix. CloneableBean can take an ignore-properties set\
    This change is useful for bean that have convenience properties
    mapping to other properties. SyndFeed and SyndEntry that map some of
    its properties (ie publishedDate, author, categories) to DC Module
    properties. There is not point cloning the convenience ones as they
    are just facades.\
    This fixes a SyndFeedImpl cloning problem with the categories. There
    is a package private list for the categories to DC subject mapping.
    The problem was related to accesibility of this package private list
    implementation by the CloneableBean. The change enables the
    blacklisting of certain properties (in this categories) when
    cloning.
16. Change. Refactored Parsers/Generator classes\
    Introduced a base parser and base generator to handle modules. For
    the feed types that define modules support, the modules have to be
    defined the rome.properties file. For RSS 1.0 and Atom 0.3 Dublin
    Core and Syndication modules are defined, the first one at feed and
    entry level the second one at feed level. Note this was already done
    but hardwired in the specific feed type parsers and generators, now
    it is done in the base parser and base generator. Some code clean up
    and removal of duplicated code was also done.
17. Change. Defined and implemented precedence order for native and
    module elements in feeds\
    For feeds supporting modules, some of the module defines elements
    collide with the feed native elements (this depends on the feed
    type, and it may affect the data such as publish-date, author,
    copyright, categories). The SyndFeed and SyndEntry properties
    documented as convenience properties are (can be) affected. The
    convenience properties map into DC Module properties.\
    Rome now defines precedence of feed native elements over module
    elements when converting from WireFeed to SyndFeed. This is, in the
    situation of a clash, the native element data prevails and the the
    module element data is lost.\
    When converting from SyndFeed to WireFeed, if a SyndFeed convenience
    property has a native mapping in the target feed type it will be in
    both the native element and the DC Module element if the feed type
    defines support for the DC module. The data will appear twice in the
    feed, in the native elements and in the DC module elements.
18. Change. Module namespaces are defined at root element\
    Module namespaces are always defined in the root element. The
    ModuleGenerator interface has a new method that returns all the
    namespaces used by the module, the generators use the namespaces
    returned by this method to inject them in the feed root element.
19. Change/Fix. Test cases refactoring\
    Some refactoring in the test cases for the Synd\* entities. Some
    minor bugs (typos in constants) were found and fixed in the
    parsers.\
    The Ops and Synd tests are 100% complete. Not that they test 100% of
    Rome functionality but 100% of what they suppose to test.
20. Fix. Atom 0.3 content elements with XML mode were not being parsed
    and converted properly\
    When mode is XML, free text was not being processed, only
    sub-elements were being processed.\
    The Parser when parsing content with XML mode skips Atom namespace
    in the output of the processed fragment.\
    The Converter when going down pushes content data using XML mode,
    which is Atom\'s default (it was ESCAPED before).
21. Addition. Constraints in data length and number of items are
    observed by RSS generators\
    As part of the generators refactoring the generators now verify the
    data in the Rome beans does not generate invalid feeds.
22. Addition. New XmlReader that detects charset encoding\
    The XmlReader class handles the charset encoding of XML documents in
    Files, raw streams and HTTP streams. It following the rules defined
    by HTTP, MIME types and XML specifications. All this is nicely
    explained by Mark Pilgrim in his blog,
    [http://diveintomark.org/archives/2004/02/13/xml-media-types
    (Archived)](https://web.archive.org/web/20060706153721/http://diveintomark.org/archives/2004/02/13/xml-media-types){.externalLink}
23. Rome now uses JDOM 1.0\
    Dependencies have been updated to use JDOM 1.0. No code changes were
    needed because of this.
24. Change. The length property in the RSS Enclosure bean is now a long\
    It used to be an int, but as it is meant to specify arbitrary
    lengths (in could be in the order of several megabytes) it was
    changed to be a long.
25. Addition. SyndFeed and SyndEntry beans have a \'uri\' property\
    It is used with RSS0.94 & RSS2.0 guid elements and with Atom0.3 id
    elements. Refer to [Feed and entry URI
    Mapping](./RomeV0.4FeedAndEntryURIMapping.html) for a full
    explanation.
26. Addition. New sample, FeedServlet\
    Added a new sample, FeedServlet. This servlet creates a feed and
    returns a feed.
27. Change. RSS0.91 Userland and RSS0.91 Netscape are handled as
    different feed types\
    Instead having a single set of parser/converter/generator
    implementations there is one set for each one of them. This allows
    to differenciate incoming RSS0.91 Userland feeds from RSS0.91
    Netscape feeds as well as choosing which one to generate.
:::

::: section
### Changes made from v0.2 to v0.3

1.  Changed loading mechanism for parsers, generators and converters\
    Previous mechanism was complicated and it wouldn\'t work in server
    environments where you cannot alter System properties at will.\
    Now there are no properties to set. Just including a rome.properties
    file in the root of a JAR file will make Rome to load any parser,
    generator or converter defined in the properties file and included
    in the JAR file (To be documented).
2.  Added Modules support to RSS 2.0 parser and generator\
    We were only looking for modules in RSS 1.0 and Atom 0.3. Now we
    also look in RSS 2.0.
3.  Modules parsers and generators are now per feed type\
    They were global, now each feed type parser and generator has it\'s
    own set of modules parsers and generators. They are configurable in
    the rome.properties file.
4.  All parsers, generators and converters are loaded once per
    definition\
    There were some cases we were loading them on every
    WireFeedParser/WireFeedGenerator instantiation. As they are all
    thread-safe we just use one instance.
5.  Changed some implementation (hidden) class names\
    Some typos corrections and adding consistency to the naming.
6.  Added Fetcher module\
    Added a HTTP fetcher with conditional get and gzip support (See
    modules/fetcher)
7.  Modified the samples module build to fix error\
    Previouly some people were having a maven error trying to run some
    of the samples. This should now be fixed
8.  Removed methods with byte stream signatures from Rome IO classes\
    They have no way to know what the encoding is or has to be and they
    are using the platform default. That does not always work, for
    example when doing HTTP where the default is ISO-8859-1.\
    Leaving the char streams one and let the developer to do the right
    thing (Rome cannot do any magic here).
9.  Added getModule(String uri) to SyndFeedI, Atom Feed and RSS Channel\
    There was not way to obtain a specific module using the module URI,
    you had to obtain the module list and iterate looking for the
    module. The getModule(string uri) is a convenience method in all the
    feed beans to do that.
10. New. Added \'encoding\' property to WireFeed and SyndFeedI/SyndFeed\
    Impact: It affects RSS, Atom and SyndFeed syndication beans.\
    It\'s not being set or use by parsers and generators as they always
    deal with a char strean where the charset is already set.\
    The converters, going from Channel/Feed 2 SyndFeed and vice versa
    are wired to pass the encoding if set.
11. Fix. CloneableBean array cloning bug\
    Impact: It affects all Rome beans as they all extend ObjectBean that
    uses CloneableBean\
    Arrays were not being cloned but copy by reference. This was
    affecting all Rome beans as they all extend ObjectBean.
12. Change. CloneableBean, Basic (primitives & string) types not cloned
    anymore\
    Impact: It affects all Rome beans as they all extend ObjectBean that
    uses CloneableBean\
    Basic types are inmutable, no need to clone them. As things are done
    using reflection there were unnecessary objects creation.
13. Change. CloneableBean, added Date to list of Basic types\
    Impact: It affects all Rome beans as they all extend ObjectBean that
    uses CloneableBean\
    Same reasoning as #12
14. Change/Fix. EqualsBeans, works on defined class\
    Impact: It affects all Rome beans as they all extend ObjectBean,
    which uses EqualsBean\
    EqualsBean checks for equality by comparing all properties. Until
    now it was doing this using all properties of the class implementing
    the bean. This behavior is not correct as implementations may have
    other properties than the defined in the public interface (and
    example is: a SyndFeedI implementation for Hibernate that has to
    have an ID field, and this would apply to all bean interfaces).\
    Because of this change, both EqualsBean and ObjectBean take a Class
    parameter in the constructor. Equals will limit the comparison for
    equality to the properties of that class. If there is not interface
    for the bean, just the bean implementation (this is the case of
    Channel and Feed), the implementation Class is passed
15. Change/Fix. ToStringBean, works on defined class\
    Same as #14 but on ToStringBean instead EqualsBean
16. New. Added copyFrom functionality to synd.\* and module.\* beans\
    Impact: It affect all synd.\* and module.\* beans and other classes
    that extend ObjectBean.\
    A new interface \'CopyFrom\' has been added to commons.\
    synd.\* and module.\* bean interfaces implement this interface and
    their implementations implement the copyFrom functionality.\
    The copyFrom functionality allows copying a complete bean feed from
    one implementation to another. The obvious use case is from the
    default bean implementation to a persistent aware (ie Hibernate)
    implementation.\
    The implementation uses the same pattern used by EqualsBean,
    ConeableBean, it\'s in the synd.impl.\* package, the supporting
    class is call SyndCopyFrom.\
    Note that SyndCategories does not support the copyFrom functionality
    as it\'s just a convenience way of accessing DCModule\'s subjects
    (where DCModule supports copyFrom). The short explanation is that
    categories still work and are there after a copyFrom.
17. Fix. WireFeed constructor was passing the WireFeed.class to
    ObjectBean\
    rss.Channel, atom.Feed & WeatherChannel constructor implementations
    only, no change to their signatures.\
    The class is used for property scanning for toString, equals
    behavior of the ObjectBean.
18. Fix. copyFrom was failing with Enum properties\
    It was not possible to do a copyFrom on a feed with SyModule data as
    it had Enum properties.\
    Wrong comparison of classes when checking basic types, in the case
    of enumeration classes has to check it extends Enum not equality.
19. Fix. SyndFeed and SyndEntry where losing Modules\
    If they had modules other than DCModule they were being dropped when
    accessing convenience methods (getCategories, getLanguage, etc).\
    The Synd\* convenience methods just map to the DCModule properties.
    If there is no DCModule a new one is created if needed. When this
    happened instead bean added to the list of current modules a new
    list with just the DCModule was being set.
20. New Sample and tutorial for creating a feed\
    A new sample and tutorial that creates and writes a feed has been
    added.
21. New Sample showing how to add a Module bean, parser and generator to
    Rome\
    This sample defines a dummy module for use with RSS 1.0 (it can work
    also with RSS 2.0 and Atom 0.3). Still have to write tutorial
22. Undoing change #13 as Date is not inmutable\
    The Date class is not inmutable, CloneableBean now clones Date
    properties.
:::

::: section
### Changes made from v0.1 to v0.2

1.  FeedInput, added default constructor. Semantics is \'validation
    off\'\
    We forgot to added it when it was added to SyndFeed.\
    NOTE that validation is not implemented yet. We need
    DTDs/XML-Schemas for the different feed syndication types.
2.  FeedOutput and SyndOutput outputW3CDom() method typo correction\
    Fixed typo in outputW3CDom() method, it was ouptutW3CDom().
3.  AbstractFeed, renamed to WireFeed\
    Never liked Abstract in the name. From Java inheritance it makes
    sense, but from the syndication feed perspective doesn\'t. It\'s the
    super class of the 2 wire feed beans Rome has, RSS Channel and Atom
    Feed.
4.  Renamed SyndFeed createRealFeed(feedType) method to
    createWireFeed(feedType)\
    For consistency with change #3
5.  SyndFeed, added feedType property\
    Read/write property. It indicates what was the feed type of the
    WireFeed the SyndFeed was created from. And the feed type a WireFeed
    created with createWireFeed() will have.
6.  WireFeed, renamed type property to feedType\
    For consistency with #5. Also it\'s more clear what the type is
    about.
7.  Overloaded SyndFeed createRealFeed() with a no parameter signature
8.  FeedOutput, removed feed type from constructor\
    It now uses the WireFeed feedType property.
9.  SyndOutput, removed feed type from constructor\
    It now uses the SyndFeed feedType property define in #5.
10. FeedOutput, removed getType() method\
    Now FeedOutput uses the WireFeed feedType property.
11. Removed dependency on Jakarta Commons Codec library\
    We were using the Codec component to do Base64 encoding/decoding.
    Based on feedback to reduce component depencies we\'ve removed this
    one (yes, we implemented a Base64 encoder/decoder).
12. Removed dependency on Xerces library\
    This was an unnecessary dependency as Rome requeries JDK 1.4+ which
    includes JAXP implementation. JDOM can use that one.
13. Renamed syndication.io classes/interfaces\
    Renaming for naming consistency and to reflect on what type of feed
    they work on.

    ```
        FeedInput     --> WireFeedInput
        FeedOutput    --> WireFeedOutput
        FeedParser    --> WireFeedParser
        FeedGenerator --> WireFeedGenerator
        SyndInput     --> SyndFeedInput
        SyndOutput    --> SyndFeedOutputt
    ```
14. Removed syndication.util package, PlugableClasses is now private\
    The PlugableClasses class has no business in Rome public API, it\'s
    implementation specific, it has been hidden (it should be replaced
    later with a micro-container).
15. Added samples to the Rome project directory structure\
    Rome samples are a sub-project located at rome/modules/sample.
:::
:::