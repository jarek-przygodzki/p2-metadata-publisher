package ca.richer.releng.eclipse.p2.publisher;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.p2.publisher.AbstractPublisherAction;
import org.eclipse.equinox.p2.publisher.AbstractPublisherApplication;
import org.eclipse.equinox.p2.publisher.IPublisherAction;
import org.eclipse.equinox.p2.publisher.PublisherInfo;
import org.eclipse.equinox.p2.publisher.actions.JREAction;
import org.eclipse.equinox.p2.publisher.eclipse.BundlesAction;
import org.eclipse.equinox.p2.publisher.eclipse.FeaturesAction;

/**
 * <p>
 * This application generates meta-data and artifact repositories from a set of features and bundles.
 * If -source <localdir> parameter is given, it specifies the directory under which to find the features 
 * and bundles (in the standard "features" and "plugins" sub-directories).
 * </p><p>
 * Optionally, the -features <csv of file locations> and -bundles <csv of file locations> arguments can 
 * be specified.  If given, these override the defaults derived from a supplied -source parameter.
 * </p>
 * 
 ****************************************************************************************************************************** 
 * PHIL: Note, this is essentially a copy of org.eclipse.equinox.p2.publisher.eclipse.FeaturesAndBundlesPublisherApplication,
 * except it also generates the special JRE IU that is necessary for plugins importing extension packages from the jre.
 ****************************************************************************************************************************** 
 */
public class MetadataPublisherApplication extends AbstractPublisherApplication {

	protected File[] features = null;
	protected File[] bundles = null;

	public MetadataPublisherApplication() {
		// nothing to do
	}

	protected void processParameter(String arg, String parameter, PublisherInfo pinfo) throws URISyntaxException {
		super.processParameter(arg, parameter, pinfo);

		if (arg.equalsIgnoreCase("-features")) //$NON-NLS-1$
			features = createFiles(parameter);

		if (arg.equalsIgnoreCase("-bundles")) //$NON-NLS-1$
			bundles = createFiles(parameter);
	} 
 
	private File[] createFiles(String parameter) {
		String[] filespecs = AbstractPublisherAction.getArrayFromString(parameter, ","); //$NON-NLS-1$
		File[] result = new File[filespecs.length];
		for (int i = 0; i < filespecs.length; i++)
			result[i] = new File(filespecs[i]);
		return result;
	}

	protected IPublisherAction[] createActions() {
		ArrayList result = new ArrayList();
		if (features == null)
			features = new File[] {new File(source, "features")}; //$NON-NLS-1$
		result.add(new FeaturesAction(features));
		if (bundles == null)
			bundles = new File[] {new File(source, "plugins")}; //$NON-NLS-1$
		result.add(new BundlesAction(bundles));
        result.add(new JREAction((File) null)); // Phil: Always create the JRE configuration IU[
		return (IPublisherAction[]) result.toArray(new IPublisherAction[result.size()]);
	}
}