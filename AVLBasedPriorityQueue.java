
public class AVLBasedPriorityQueue<T extends Comparable<T>>
{
	class Node<T extends Comparable<T>>
	{
		public T data;
		public Node<T> left;
		public Node<T> right;
		public Node<T> parent;
		
		public Node(T nodeData)
		{
			data = nodeData;
			left = null;
			right = null;
			parent = null;
		}
	}
	
	public Node rootNode;
	private boolean validOffer;
	
	public AVLBasedPriorityQueue() 
	{
		rootNode = null;
	}
	
	public boolean offer(T data)  // add value into the right place in the AVL tree
	{
		validOffer = true;
		rootNode = offerHelper(rootNode, null, data);
		return validOffer;
	}
	
	private Node offerHelper(Node currentNode, Node parent, T data)
	{
		if(currentNode == null)
		{
			currentNode = new Node(data);
			currentNode.parent = parent;
		}
		else if(data.compareTo((T)currentNode.data) < 0)
		{
			currentNode.left = offerHelper(currentNode.left, currentNode, data);
		}
		else if(data.compareTo((T)currentNode.data) > 0)
		{
			currentNode.right = offerHelper(currentNode.right, currentNode, data);
		}
		else
		{
			validOffer = false;
			return null;
		}
		
		currentNode = Balance(currentNode);
		
		return currentNode;
	}
	
	public T poll()
	{
		Node currentNode = rootNode;
		while(currentNode.left != null)
		{
			currentNode = currentNode.left;
		}
		T removedData = (T)currentNode.data;
		rootNode = pollHelper(rootNode);
		
		return removedData;
	}
	
	private Node pollHelper(Node node)
	{
		Node currentNode = node;
		if(currentNode.left != null)
		{
			currentNode.left = pollHelper(currentNode.left);
		}
		else
		{
			Node tempChild = currentNode.right;
			currentNode = tempChild;
		}
		
		if(currentNode != null)
		{
			currentNode = Balance(currentNode);
		}
		
		return currentNode;
	}
	
	public T peek()  // see, but do not remove the highest priority value
	{
		return (T)rootNode.data;
	}
	
	private Node Balance(Node subRootNode)
	{
		int balanceFactor = calculateBalanceFactor(subRootNode);
		if(balanceFactor >= 2)
		{
			Node child = subRootNode.left;
			if(calculateBalanceFactor(child)  == -1)
			{
				subRootNode.left = rotateLeft(subRootNode.left);
			}
			subRootNode = rotateRight(subRootNode);
		}
		else if(balanceFactor <= -2)
		{
			Node child = subRootNode.right;
			if(calculateBalanceFactor(child)  == 1)
			{
				subRootNode.right = rotateRight(subRootNode.right);
			}
			subRootNode = rotateLeft(subRootNode);
		}
		return subRootNode;
	}
	
	private int calculateBalanceFactor(Node node)
	{
		return getHeight(node.left) - getHeight(node.right);
	}
	
	private int getHeight(Node currentNode)
	{
		int height;
		if(currentNode == null)
		{
			return 0;
		}
		else
		{
			height = Math.max( getHeight(currentNode.left), getHeight(currentNode.right) ) + 1;
			return height;
		}
	}
	
	private Node rotateLeft(Node node)
	{
		Node rebalancedNode = node;
		Node tempChild = rebalancedNode.right.left;
		rebalancedNode.right.left = rebalancedNode;
		rebalancedNode = rebalancedNode.right;
		rebalancedNode.left.right = tempChild;
		
		return rebalancedNode;
	}
	
	private Node rotateRight(Node node)
	{
		Node rebalancedNode = node;
		Node tempChild = rebalancedNode.left.right;
		rebalancedNode.left.right = rebalancedNode;
		rebalancedNode = rebalancedNode.left;
		rebalancedNode.right.left = tempChild;
		
		return rebalancedNode;
	}
	
	public void subtreePrint(Node root)
	{
		System.out.println(root.data);
		String g = "";
		if(root.left == null)
			g += "null" + "   ";
		else
			g += root.left.data + "   ";
		
		if(root.right == null)
			g += "null";
		else
			g += root.right.data;
		
		System.out.println(g);
	}
}










//public Node(T nodeData, Node leftNode, Node rightNode)
//{
//	data = nodeData;
//	left = leftNode;
//	right = rightNode;
//	parent = null;
//}
//
//public Node(T nodeData, Node leftNode, Node rightNode, Node parentNode)
//{
//	data = nodeData;
//	left = leftNode;
//	right = rightNode;
//	parent = parentNode;
//}
