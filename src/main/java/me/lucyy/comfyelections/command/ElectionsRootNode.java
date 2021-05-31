package me.lucyy.comfyelections.command;

import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.command.node.SubcommandNode;
import me.lucyy.squirtgun.platform.PermissionHolder;
import java.lang.reflect.Field;

public class ElectionsRootNode extends SubcommandNode<PermissionHolder> {

	private final ElectionManager manager;

	public ElectionsRootNode(ElectionManager manager) {
		super("elections", null,
				true,
				new CreateElectionNode(manager),
				new ListElectionsNode(manager),
				new RegisterForElectionNode(manager),
				new VoteNode(manager)
		);
		this.manager = manager;

		// reflection time! :D TODO fix this in squirtgun
		try {
			Field helpNodeField = SubcommandNode.class.getDeclaredField("helpNode");
			helpNodeField.setAccessible(true);
			helpNodeField.set(this, new ListElectionsNode(manager));
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// something broke D:
			e.printStackTrace();
		}

	}

	@Override
	public String getDescription() {
		return "Lists all current elections";
	}
}
