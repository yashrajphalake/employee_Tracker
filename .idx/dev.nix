# To learn more about how to use Nix to configure your environment
# see: https://firebase.google.com/docs/studio/customize-workspace
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-24.05"; # or "unstable"

  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.jdk8
    pkgs.maven
    pkgs.tomcat9
  ];

  # Sets environment variables in the workspace
  env = {
    TOMCAT_HOME = "${pkgs.tomcat9}";
  };
  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      "vscjava.vscode-java-pack"
    ];

    # Enable previews
    previews = {
      enable = true;
      previews = {
        web = {
          command = [
            "sh"
            "-c"
            "export CATALINA_BASE=$HOME/tomcat_base && mkdir -p $CATALINA_BASE && cp -r $TOMCAT_HOME/conf $CATALINA_BASE/ && mkdir -p $CATALINA_BASE/webapps && mvn -f EmployeeTracker_secure/pom.xml clean install && cp EmployeeTracker_secure/target/EmployeeTracker.war $CATALINA_BASE/webapps/ROOT.war && catalina.sh run"
          ];
          manager = "web";
        };
      };
    };

    # Workspace lifecycle hooks
    workspace = {
      # Runs when a workspace is first created
      onCreate = {};
      # Runs when the workspace is (re)started
      onStart = {};
    };
  };
}
