package raj.outlet_form.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import raj.outlet_form.databinding.FragmentFirstBinding
import raj.outlet_form.utilities.FragmentUtil
import raj.outlet_form.utilities.InjectorUtils
import raj.outlet_form.utilities.UserClickCallbacks


class Fragmentfirst : Fragment(), UserClickCallbacks
{
    override fun onUserClick(view: CharSequence) {
    }

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var injector: InjectorUtils
    private lateinit var list: List<Element>
    var adapter :AdapterRecyclerViewImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!

    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        //val retView = inflater.inflate(raj.outlet_form.R.layout.fragment_first, container, false)
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, raj.outlet_form.R.layout.fragment_first, container, false) as FragmentFirstBinding
        binding.state = UiState.Loading
        getList()
        adapter?.update(list)

//



        binding.buttonFrame.setOnClickListener{

            getNextPage()

        }


        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    fun getList()
    {

        adapter = AdapterRecyclerViewImpl(viewModel,this)
        list = viewModel.getAllWords(1).value!!


        val layoutManager = LinearLayoutManager(activity)
        binding.list.adapter = adapter
        binding.list.layoutManager = layoutManager
        binding.state = UiState.Data

    }



    fun getNextPage()
    {


        val fragmentTransaction = fragmentManager?.beginTransaction()

        var fragmentTwo = FragmentUtil.getFragmentByTagName(fragmentManager, "second")

        // Because fragment two has been popup from the back stack, so it must be null.
        if (fragmentTwo == null) {
            fragmentTwo = FragmentSecond()


        }
        // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
        // This action will remove Fragment one and add Fragment two.
        fragmentTransaction?.replace(raj.outlet_form.R.id.fragment_back_stack_frame_layout, fragmentTwo, "second")

        // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
        fragmentTransaction?.addToBackStack(null)

        fragmentTransaction?.commit()

        FragmentUtil.printActivityFragmentList(fragmentManager)
    }

    override fun onResume() {
        super.onResume()
        Log.d("method invock", ": onResume")

    }
}


