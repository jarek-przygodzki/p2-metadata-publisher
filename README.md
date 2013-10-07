p2-metadata-publisher
=====================
Based on [code](http://www.eclipse.org/forums/index.php/mv/msg/159026/502559/#msg_502559) published by **Phil Denis** on _Eclipse Community Forums_ in repose to question ["[p2] how to generate "a.jre.javase" IU?"](http://www.eclipse.org/forums/index.php/t/159026/). In case the web page goes down I'm quoting his answer here

>You cannot use FeaturesAndBundles publisher to do this (__generate "a.jre.javase" IU__). Metadata generation was completely refactored.

>The "old" EclipseGenerator application used to build a JRE IU in content.xml. However, it should not be used anymore IMHO because it does not process p2.inf (or Advice files). Now this functionality is split across several different >IPublisherActions (see attached pic of IPublisherAction's hierarchy). An AbstractPublisherApplication has a method called 
>IPublisherAction[] createActions()
>that specifies what the publisher application will create.

>FeaturesAndBundlesPublisher only creates 2 actions .. FeaturesAction and BundlesAction. The code to generate the JRE IU is located in JREAction. To my knowledge, none of the publisher applications that ship with Equinox use the JREAction.

>So, to do this you need to create your own publisher application. I have taken FeaturesAndBundlesPublisher and modified it to also create the JRE IU.

>Please note, however, that you only need the JRE IU in content.xml if you are trying to install some bundle that uses "extension packages" from the JRE. For example, org.w3c.dom or javax.swing.
