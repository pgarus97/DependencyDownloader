# DependencyDownloader
Prototype tool to download dependencies of a project.

You need to put a pom.xml and/or a package.json into the "Input" folder.

Prerequisites:
Download and install this version of scancode-toolkit into the main directory next to "Input" : https://github.com/nexB/scancode-toolkit/releases/tag/v21.3.31

You can install it with scancode -h in the command line (requires Python 3.6+) or with other methods listed here: https://scancode-toolkit.readthedocs.io/en/latest/getting-started/install.html

The result of the scan will appear in the scancode toolkit directory under "output.json"

You can run the tool by running java -jar DependencyDownloader.jar

For testing purposes, the output folder in the scancode directory must be cleared by hand before running the tool again.