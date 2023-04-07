private void heapify(int where) { 
 		//
 		// As described in lecture
	    //  FIXME
		// FIXME
 		//
		ticker.tick();
		// rChild = 2*i+1
		int rChild = rightChild(where);
		// lChild = 2*i
		int lChild = leftChild(where);
		// test if parent is out of bound
		if (where > size) {
			return;
		}
		else {
			// parent value
		T parentValue = array[where].getValue();
		// checking if there's no children
		if (rChild > size && lChild > size) {
			return;
			}

			// checking if there's no right child and there's left child
		if (rChild > size && lChild <= size) {
				// get the left value
				T left = array[lChild].getValue();
			// swap parent with left, left is less than parent
				if (left.compareTo(parentValue) < 0) {
					compareNTrack(lChild, where);
					heapify(lChild);
				ticker.tick();
				} else {
					// check if parent is greater than or equal left do nothing
				return;
			}

		}
			// checking if there are two children

			if (rChild <= size && lChild <= size) {

				// get the left value

			T left = array[lChild].getValue();
			// right value
				T right = array[rChild].getValue();
			// check if parent is less than both do nothing

				if (left.compareTo(parentValue) > 0 && right.compareTo(parentValue) > 0) {
 					return;
			} else {
				// check left is less than right and parent

				if (left.compareTo(right) < 0 && left.compareTo(parentValue) < 0) {
						compareNTrack(lChild, where);
						heapify(lChild);
						ticker.tick();
 					// check right is less than left and parent


 					} else if (right.compareTo(left) < 0 && right.compareTo(parentValue) < 0) {
 						compareNTrack(rChild, where); 
					heapify(rChild);
						// check left = right, pick the left side
					} else if (left.compareTo(parentValue) < 0 && right.compareTo(parentValue) < 0

  						&& left.compareTo(right) == 0) {
  					compareNTrack(lChild, where);
 						heapify(lChild);
  					ticker.tick();
			        } else {


  
						return; 