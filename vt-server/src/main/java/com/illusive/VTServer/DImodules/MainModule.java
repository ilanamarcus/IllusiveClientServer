package com.illusive.VTServer.DImodules;

import com.google.inject.AbstractModule;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(VirustotalPublicV2.class).to(VirustotalPublicV2Impl.class);
		bind(Test.class).to(TestImpl.class);

	}

}
