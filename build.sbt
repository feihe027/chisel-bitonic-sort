


// See README.md for license details.

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.github.wolftungsten"

lazy val root = (project in file("."))
  .settings(
    name := "chisel-bitonic-sort",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.6.0",
      "edu.berkeley.cs" %% "chiseltest" % "0.6.2" % "test"
    ),
    scalacOptions ++= Seq(
     // "-Xsource:2.11",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
        "-unchecked",
      "-Xfatal-warnings",
      "-language:reflectiveCalls",
      // Enables autoclonetype2 in 3.4.x (on by default in 3.5)
      //"-P:chiselplugin:useBundlePlugin"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.6.0" cross CrossVersion.full),
    //addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )


