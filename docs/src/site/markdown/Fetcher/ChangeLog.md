::: section
## Change Log

::: section
### Prior to first release (on the way to v0.3)

1.  Updated to handle removal of IO methods using byte streams\
    Byte Stream IO was removed from Rome itself. The Rome Fetcher is now
    updated to support this
2.  Add FeedFetcherI interface and FeedFetcherFactory class\
    There is now a FeedFetcherI interface, which FeedFetcher implements.
    Use FeedFetcherFactory to create instances of FeedFetcher (as
    suggested by Joseph Ottinger) (FeedFetcherFactory was later removed)
3.  Event Support Added to FeedFetcherI\
    The FeedFetcherI interface now supports feed polled, feed retrieved
    and feed unchanged events
4.  Samples added\
    Samples are now included with the Rome Fetcher
5.  Unit Tests Added\
    JUnit based tests which invoke the Rome Fetcher against an embedded
    Jetty webserver are now included
6.  Bug fixes in the FeedFetcher event model\
    The JUnit test suite uncovered some bugs in the event model used by
    the FeedFetcher. These bugs are now fixed.
7.  Refactored the SyndFeedInfo class\
    SyndFeedInfo now extends ObjectBean
8.  Removed FeedFetcherFactory\
    The benefit of the FeedFetcherFactory was arguable. Now the client
    code will need to manage the creation of specific implementations of
    the FeedFetcher
:::

::: section
### Prior to second release (on the way to v0.4)

1.  Refectored to match Rome naming standards\
    FeedFetcherI renamed to FeedFetcher\
    #. New FeedFetcher Implementation\
    HttpClientFeedFetcher uses the Apache Commons HTTP Client
2.  Abstract test classes excluded in project.xml\
    Tests now run correctly under Maven
3.  Added GZip support to HttpClientFeedFetcher\
    HttpClientFeedFetcher now supports GZip compression. Tests have been
    added.
:::

::: section
### Prior to third release (on the way to v0.5)

1.  SyndFeedInfo implements Serializable\
    SyndFeedInfo implements Serializable to make it easier to store
2.  Support for rfc3229 delta encoding\
    The Fetcher now supports rfc3229 delta encoding. See
    [http://www.ietf.org/rfc/rfc3229.txt](http://www.ietf.org/rfc/rfc3229.txt){.externalLink}
    and
    [http://bobwyman.pubsub.com/main/2004/09/using_rfc3229_w.html](http://bobwyman.pubsub.com/main/2004/09/using_rfc3229_w.html){.externalLink}.
    Note that this is support is experimental and disabled by default
:::

::: section
### Prior to 0.6

1.  Feed passed to FetcherEvents\
    When a feed is retrieved it is now passed to the Fetcher Event. This
    makes it easier to code applications using an event oriented style.
:::

::: section
### Prior to 0.7

1.  Fix for URL Connection leak\
    In some circumstances URLConnection objects were not closed. This
    could cause problems in long-running application.
:::

::: section
### 0.8 was never released
:::

::: section
### Prior to 0.9

1.  Fix for potential synchronization issue\
    There was the possibility of synchronization issues in the
    FeedFetcher. Fixed, thanks to suggestions from Javier Kohen.
2.  New LinkedHashMapFeedInfoCache FeedFetcherCache implementation\
    The new LinkedHashMapFeedInfoCache has the advantage that it will
    not grow unbound
:::

::: section
### Prior to 1.0RC2

1.  BeanInfo class added for AbstractFeedFetcher\
    com.rometools.rome.fetcher.impl.AbstractFeedFetcherBeanInfo was
    created to allow introspection to correctly find the events
2.  Callback to allow access to HttpClient HttpMethod object\
    Add a HttpClientMethodCallbackIntf to allow the calling code to
    modify the HttpClient HttpMethod used to make the request (eg, add
    additinal headers, etc.) Also fixes a reported bug where the user
    agent wasn\'t being set properly
3.  Support for clearing cache\
    See
    [http://java.net/jira/browse/ROME-119](http://java.net/jira/browse/ROME-119){.externalLink}
    for details
:::

::: section
### Prior to 1.0

1.  Support for preserving wire feed data.\
    The fetcher now has a setPreserveWireFeed() method which will setup
    ROME to preserve WireFeed data. See
    [PreservingWireFeeds](http://rometools.github.io/rome/PreservingWireFeeds.html){.externalLink}
    for further information.
:::
:::