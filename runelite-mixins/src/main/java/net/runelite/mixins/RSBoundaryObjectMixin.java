package net.runelite.mixins;

import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.geometry.Shapes;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSBoundaryObject;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSRenderable;

import java.awt.Shape;

@Mixin(RSBoundaryObject.class)
public abstract class RSBoundaryObjectMixin implements RSBoundaryObject
{
	@Shadow("client")
	private static RSClient client;

	@Inject
	private int wallPlane;

	@Inject
	@Override
	public int getPlane()
	{
		return wallPlane;
	}

	@Inject
	@Override
	public void setPlane(int plane)
	{
		this.wallPlane = plane;
	}

	@Inject
	public RSModel getModelA()
	{
		RSRenderable entity = getRenderable1();
		if (entity == null)
		{
			return null;
		}

		if (entity instanceof Model)
		{
			return (RSModel) entity;
		}
		else
		{
			return entity.getModel();
		}
	}

	@Inject
	public RSModel getModelB()
	{
		RSRenderable entity = getRenderable2();
		if (entity == null)
		{
			return null;
		}

		if (entity instanceof Model)
		{
			return (RSModel) entity;
		}
		else
		{
			return entity.getModel();
		}
	}

	@Inject
	@Override
	public Shape getClickbox()
	{
		Shape clickboxA = Perspective.getClickbox(client, getModelA(), 0, getX(), getY(), getZ());
		Shape clickboxB = Perspective.getClickbox(client, getModelB(), 0, getX(), getY(), getZ());

		if (clickboxA == null && clickboxB == null)
		{
			return null;
		}

		if (clickboxA != null && clickboxB != null)
		{
			return new Shapes(new Shape[]{clickboxA, clickboxB});
		}

		if (clickboxA != null)
		{
			return clickboxA;
		}

		return clickboxB;
	}

	@Inject
	@Override
	public Shape getConvexHull()
	{
		RSModel model = getModelA();

		if (model == null)
		{
			return null;
		}

		int tileHeight = Perspective.getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

		return model.getConvexHull(getX(), getY(), 0, tileHeight);
	}

	@Inject
	@Override
	public Shape getConvexHull2()
	{
		RSModel model = getModelB();

		if (model == null)
		{
			return null;
		}

		int tileHeight = Perspective.getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

		return model.getConvexHull(getX(), getY(), 0, tileHeight);
	}
}
